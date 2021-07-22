FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7

COPY target/*.jar app.jar
ENV TZ=Europe/Amsterdam
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom"]
CMD ["-jar","app.jar"]
