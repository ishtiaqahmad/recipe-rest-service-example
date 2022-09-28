package com.capgemini;

import com.capgemini.testutil.ReflectTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTest {

    /**
     * Must be set by all subclasses of this class to enable testing of this class.
     */
    protected Class<?> classUnderTest;

    /**
     * Verifies that the field with the given <code>fieldName</code> of the class under test
     * has a @JsonProperty annotation with the given <code>value</code>.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param fieldName
     * @param value
     */
    protected void verifyJsonPropertyValueOfField(boolean checkCount, String fieldName, String value) {
        verifyFieldAnnotations(checkCount, fieldName, JsonProperty.class);
        Assertions.assertEquals(value, ReflectTool.getFieldAnnotation(classUnderTest, fieldName, JsonProperty.class).value());
    }

    /**
     * Verifies that the method with the given <code>methodName</code> of the class under test
     * has a @JsonProperty annotation with the given <code>value</code>.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param methodName
     * @param value
     */
    protected void verifyJsonPropertyValueOfMethod(boolean checkCount, String methodName, String value) {
        verifyMethodAnnotations(checkCount, methodName, JsonProperty.class);
        Assertions.assertEquals(value, ReflectTool.getMethodAnnotation(classUnderTest, methodName, JsonProperty.class).value());
    }

    /**
     * Verifies that the class under test has the @JsonInclude annotation with the given <code>include</code>.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param include
     */
    protected void verifyJsonIncludeValueOfClass(boolean checkCount, Include include) {
        verifyClassAnnotations(checkCount, JsonInclude.class);
        Assertions.assertEquals(include, ReflectTool.getClassAnnotation(classUnderTest, JsonInclude.class).value());
    }

    /**
     * Verifies that the method with the given <code>methodName</code> of the classUnderTest
     * has the @JsonSerialize annotation with the given <code>usingClass</code> class.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param methodName
     * @param usingClass
     */
    protected void verifyJsonSerializerUsingAnnotationOfMethod(boolean checkCount, String methodName, Class<?> usingClass) {
        verifyMethodAnnotations(checkCount, methodName, JsonSerialize.class);
        Assertions.assertEquals(usingClass, ReflectTool.getMethodAnnotation(classUnderTest, methodName, JsonSerialize.class).using());
    }

    /**
     * Verifies that the field with the given <code>fieldName</code> of the classUnderTest
     * has the @JsonSerialize annotation with the given <code>usingClass</code> class.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param fieldName
     * @param nullsUsingClass
     */
    protected void verifyJsonSerializerNullsUsingAnnotationOfField(boolean checkCount, String fieldName, Class<?> nullsUsingClass) {
        verifyFieldAnnotations(checkCount, fieldName, JsonSerialize.class);
        Assertions.assertEquals(nullsUsingClass, ReflectTool.getFieldAnnotation(classUnderTest, fieldName, JsonSerialize.class).nullsUsing());
    }

    /**
     * Verifies that the class under test overrides the <code>toString()</code> method
     */
    protected void verifyToStringIsOverridden() {
        try {
            Class<?> declaringClass = classUnderTest.getMethod("toString").getDeclaringClass();
            Assertions.assertSame (classUnderTest, declaringClass,"Override of toString() expected.");
        } catch (NoSuchMethodException | SecurityException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Verifies that the class under test overrides the <code>equals()</code> method
     */
    protected void verifyEqualsIsOverridden() {
        try {
            Class<?> declaringClass = classUnderTest.getDeclaredMethod("equals", Object.class).getDeclaringClass();
            Assertions.assertSame(classUnderTest, declaringClass,"Override of equals(Object o) expected" );
        } catch (NoSuchMethodException | SecurityException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Verifies that the class under test overrides the <code>hashCode()</code> method
     */
    protected void verifyHashCodeIsOverridden() {
        try {
            Class<?> declaringClass = classUnderTest.getMethod("hashCode").getDeclaringClass();
            Assertions.assertSame( classUnderTest, declaringClass,"Override of hashCode() expected");
        } catch (NoSuchMethodException | SecurityException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Verifies that the class under test implements the expected interfaces.
     *
     * @param expectedInterfaces
     */
    protected void verifyImplements(Class<?>... expectedInterfaces) {
        Class<?>[] interfaces = classUnderTest.getInterfaces();

        for (Class<?> ei : expectedInterfaces) {
            if (!Arrays.asList(interfaces).contains(ei)) {
                throw new AssertionError(
                        String.format("Expected interface %s not implemented", ei.getName()));
            }
        }
        for (Class<?> ii : interfaces) {
            if (!Arrays.asList(expectedInterfaces).contains(ii)) {
                throw new AssertionError(
                        String.format("Unexpected interface %s implemented", ii.getName()));
            }
        }
    }

    /**
     * Verifies that the class under test extends the expected class.
     *
     * @param extendsClass
     */
    protected void verifyExtends(Class<?> extendsClass) {
        Class<?> extending = classUnderTest.getSuperclass();
        Assertions.assertEquals(extendsClass, extending);
    }

    /**
     * Verifies that the class under test has the expected annotations
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param expectedAnnotationClasses The expected annotations.
     */
    protected void verifyClassAnnotations(boolean checkCount, Class<?>... expectedAnnotationClasses) {
        verifyAnnotations(checkCount, expectedAnnotationClasses, classUnderTest.getAnnotations());
    }

    /**
     * Verifies that a field with the given <code>fieldName</code> of the class under test
     * has the expected annotations.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param fieldName The field to verify the annotations of.
     * @param expectedAnnotationClasses The expected annotations.
     */
    protected void verifyFieldAnnotations(boolean checkCount, String fieldName, Class<?>... expectedAnnotationClasses) {
        try {
            verifyAnnotations(checkCount,
                    expectedAnnotationClasses,
                    classUnderTest.getDeclaredField(fieldName).getAnnotations());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Verifies that a method with the given <code>methodName</code> of the class under test has
     * the expected annotations.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param methodName The name of the method to verify the annotations of.
     * @param expectedAnnotationClasses The expected annotations.
     */
    protected void verifyMethodAnnotations(boolean checkCount,
            String methodName, Class<?>... expectedAnnotationClasses) {
        try {
            verifyAnnotations(checkCount,
                    expectedAnnotationClasses,
                    classUnderTest.getDeclaredMethod(methodName).getAnnotations());
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Verifies that the given <code>method</code> has the expected annotations.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param method The method to verify the annotations of.
     * @param expectedAnnotationClasses The expected annotations.
     */
    protected void verifyMethodAnnotations(boolean checkCount,
            Method method, Class<?>... expectedAnnotationClasses) {
        verifyAnnotations(checkCount,
                expectedAnnotationClasses,
                method.getAnnotations());
    }

    /**
     * Verifies that the given <code>constructor</code> of the class under test has
     * the expected annotations.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param constructor The constructor to verify the annotations of.
     * @param expectedAnnotationClasses The expected annotations.
     */
    protected void verifyConstructorAnnotations(boolean checkCount,
            Constructor<?> constructor, Class<?>... expectedAnnotationClasses) {
        verifyAnnotations(checkCount,
                expectedAnnotationClasses,
                constructor.getAnnotations());
    }

    /**
     * Verifies that the default constructor of the class under test is private.
     */
    protected void verifyDefaultConstructorIsPrivate() {
        Assertions.assertEquals(0, classUnderTest.getConstructors().length);
        List<Constructor<?>> constructors = Arrays.asList(classUnderTest.getDeclaredConstructors());
        Constructor<?> constructor = constructors.stream()
                .filter(c -> c.getParameterTypes().length == 0)
                .findAny()
                .orElse(null);
        if (constructor == null) {
            throw new AssertionError("No (default) no arg constructor found");
        }
        Assertions.assertFalse(constructor.isAccessible());
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Verifies that the found annotations of a class match the expected annotations.
     *
     * @param checkCount When <code>true</code>, verify the number of annotations
     * @param expectedAnnotationClasses
     * @param foundAnnotations
     */
    private void verifyAnnotations(boolean checkCount,
            Class<?>[] expectedAnnotationClasses, Annotation[] foundAnnotations) {

        //length
        if (checkCount) {
            verifyAnnotationsCount(expectedAnnotationClasses, foundAnnotations);
        }

        for (Class<?> eac : expectedAnnotationClasses) {
            long cnt = Arrays.asList(foundAnnotations).stream()
                    .filter(a -> a.annotationType().equals(eac))
                    .count();
            if (cnt == 0) {
                throw new AssertionError(
                        String.format("Expected annotation of type %s not found", eac.getName()));
            }
        }
    }

    /**
     * Verifies that the number of found annotations is equal to the expected number of annotations.
     * Also checks that the value (type) of each found annotation is present in the expected annotations.
     * @param expectedAnnotationClasses
     * @param foundAnnotations
     * @throws AssertionError when either the number of found annotations does not match the number of expected annotations
     *         or one of the found annotations is not in the expected annotations.
     */
    private void verifyAnnotationsCount(Class<?>[] expectedAnnotationClasses, Annotation[] foundAnnotations) {
        List<Class<?>> expectedAnnotationClassList = Arrays.asList(expectedAnnotationClasses);
        if (expectedAnnotationClasses.length != foundAnnotations.length) {
            throw new AssertionError(String.format("Expected %d annotations, but found %d", expectedAnnotationClasses.length,
                    foundAnnotations.length));
        }
        for (Annotation fa : foundAnnotations) {
            if (!expectedAnnotationClassList.contains(fa.annotationType())) {
                throw new AssertionError(
                        String.format("Unexpected annotation of type %s found", fa.annotationType().getName()));
            }
        }
    }
}
