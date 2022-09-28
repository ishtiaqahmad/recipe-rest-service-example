package com.capgemini.testutil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Changed by Ishtiaq.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public final class ReflectTool {

    private ReflectTool() {
    }

    public static <T extends Annotation> T getMethodAnnotation(
            Class<?> c, String methodName, Class<T> annotation) {
        try {
            Method m = c.getDeclaredMethod(methodName);
            return m.getAnnotation(annotation);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Annotation> T getFieldAnnotation(
            Class<?> c, String fieldName, Class<T> annotation) {
        try {
            Field f = c.getDeclaredField(fieldName);
            return f.getAnnotation(annotation);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Annotation> T getClassAnnotation(
            Class<?> c, Class<T> annotation) {
        return c.getAnnotation(annotation);
    }
}
