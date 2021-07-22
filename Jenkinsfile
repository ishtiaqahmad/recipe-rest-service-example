pipeline {

    agent none

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        skipDefaultCheckout true
    }

    environment {
        GITLAB_CREDENTIALS_ID = "0eb90c04-ed3a-4dd2-a754-1c98cce5702b"
        NEXUS_REPO_PUBLIC = "https://nexus.pl.s2-eu.capgemini.com/nexus3/repository/maven-public"
        MAVEN_SETTINGS_CONFIG_FILE_ID = "c21f519c-5f03-4f00-abd6-470a646c4a56"


        DOCKER_REGISTRY_URL = "https://docker-registry.pl.s2-eu.capgemini.com"
        DOCKER_CREDENTIALS_ID = "0eb90c04-ed3a-4dd2-a754-1c98cce5702b"
        DOCKER_CONTAINER_PREFIX = "capgemini"
    }

    parameters {
        booleanParam(name: "isRelease", description: "Release", defaultValue: false)
        string(name: 'releaseVersion', defaultValue: 'X.X.X.X', description: 'Fill in your release version')
        string(name: 'developmentVersion', defaultValue: 'X.X.X.X-SNAPSHOT', description: 'Fill in next development version?')
    }



    stages {
        stage('Build Artifact') {
            agent {
                label 'debian'
            }

            stages {
                stage('Clean ws') {
                    steps {
                        script {
                            cleanWs()
                        }
                    }
                }

                stage('Checkout') {
                    steps {
                        checkout scm
                        script{
                            env.MAVEN_POM_GROUPID = readMavenPom().getGroupId();
                            env.MAVEN_POM_ARTIFACTID = readMavenPom().getArtifactId();
                            env.MAVEN_POM_VERSION = readMavenPom().getVersion();
                            env.MAVEN_POM_PACKAGING = readMavenPom().getPackaging();
                        }
                    }
                }

                stage('Maven deploy') {
                    steps {
                        script {
                            jdk = tool name: 'JDK 11'
                            env.JAVA_HOME = "${jdk}"

                            withMaven(
                                    maven: 'Maven361',
                                    mavenSettingsConfig: "${MAVEN_SETTINGS_CONFIG_FILE_ID}",
                                    mavenLocalRepo: '~/.m2/repository'
                            ) {
                                sh "mvn deploy"
                            }
                        }
                    }
                }
                stage('SonarQube and OWASP') {
                    parallel {
                        stage('SonarQube scanner') {
                            steps {
                                script {
                                    def SONARQUBE_HOME = tool 'SonarQube';

                                    withSonarQubeEnv() {
                                        sh "${SONARQUBE_HOME}/bin/sonar-scanner -Dsonar.sources=src/main/java -Dsonar.projectKey=${MAVEN_POM_GROUPID} -Dsonar.projectName=${MAVEN_POM_ARTIFACTID} -Dsonar.projectVersion=${MAVEN_POM_VERSION} -Dsonar.java.source=11 -Dsonar.java.binaries=target -Dsonar.dynamicAnalysis=reuseReports -Dsonar.core.codeCoveragePlugin=jacoco"
                                    }
                                }
                            }
                        }

                        stage('OWASP scanner') {
                            steps {
                                script {
                                    dependencyCheck additionalArguments: "-s './target' --format 'ALL'", odcInstallation: 'OWASP dependency check'
                                    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'

                                }
                            }
                        }
                    }
                }

                stage('Maven release') {
                    when {
                        expression {
                            params.isRelease
                        }
                    }
                    steps {
                        script {
                            jdk = tool name: 'JDK 11'
                            env.JAVA_HOME = "${jdk}"

                            sh "git config user.email technicaluser@capgemini.com"
                            sh "git config user.name technicaluser"


                            withMaven(
                                    maven: 'Maven339',
                                    mavenSettingsConfig: "${MAVEN_SETTINGS_CONFIG_FILE_ID}",
                                    mavenLocalRepo: '~/.m2/repository'
                            ) {
                                sh "mvn -B release:clean release:prepare release:perform -DdryRun=true -DignoreSnapshots=true -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${developmentVersion}"
                                sh "mvn -B release:clean release:prepare release:perform -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${developmentVersion}"
                                env.MAVEN_POM_VERSION = "${releaseVersion}"
                            }
                        }
                    }
                }

                stage('Stash Docker files') {
                    steps {
                        script {
                            stash includes: "target/*.jar,Dockerfile", name: "docker"
                        }
                    }
                }

            }
        }
        stage('Build containers') {

            agent {
                label 'docker'
            }

            stages {
                stage('unstash'){
                    steps {
                        script {
                            unstash 'docker'
                        }
                    }
                }

                stage('Build | Containers') {
                    steps {
                        script {
                            docker.withRegistry("${DOCKER_REGISTRY_URL}", "${DOCKER_CREDENTIALS_ID}") {
                                dockerImage = docker.build("${DOCKER_CONTAINER_PREFIX}${MAVEN_POM_ARTIFACTID}:${MAVEN_POM_VERSION}", " . ")
                            }
                        }
                    }
                }

                stage('Push to | Nexus registry') {
                    steps {
                        script {
                            docker.withRegistry("${DOCKER_REGISTRY_URL}", "${DOCKER_CREDENTIALS_ID}") {
                                dockerImage.push("latest")
                            }
                            docker.withRegistry("${DOCKER_REGISTRY_URL}", "${DOCKER_CREDENTIALS_ID}") {
                                dockerImage.push("${MAVEN_POM_VERSION}")
                            }
                        }
                    }
                }

            }
            post {
                always {
                    cleanWs()
                }
            }
        }

        stage('run containers on new machine') {

            agent {
                label 'ansible'
            }

            stages {
                stage('deploy with ansible') {
                    steps {
                        script {
                            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: "958f3b0b-1c0b-40e3-969e-151b8b8108b8", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                                dir("/opt/ansible/") {
                                    sh "echo ${PASSWORD} | sudo -Su ansible -s ansible-playbook -i stcvpc/test playbooks/capgemini-recipe-service-docker.yml --vault-password-file=/home/ansible/capgemini-vault-pass.txt -e env=test -e recipe_service_version=${env.MAVEN_POM_VERSION}"
                                }
                            }

                        }
                    }
                }


            }
        }
    }
}
