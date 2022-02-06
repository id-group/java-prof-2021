package ru.idgroup.otus.annotations.reflection;

import ru.idgroup.otus.annotations.annotations.After;
import ru.idgroup.otus.annotations.annotations.Before;
import ru.idgroup.otus.annotations.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationInvoker {

    public static void execute(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);

        final Method[] declaredMethods = clazz.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(Test.class))
                .forEach(method -> {
                    final Object instantiate = ReflectionHelper.instantiate(clazz);
                    try {
                        //before
                        executeAnnotation(instantiate, Before.class);
                        ReflectionHelper.callMethod(instantiate, method.getName());

                    } catch (Exception e) {
                        System.out.println("Method:" + method.getName() + " executed with exception:" + e.getMessage());
                    }
                    finally {
                        //after
                        executeAnnotation(instantiate, After.class);
                    }
                });
    }

    private static void executeAnnotation(Object invokedObject, Class<? extends Annotation> annotationClass ) {
        final Method[] declaredMethods = invokedObject.getClass().getDeclaredMethods();
        Arrays.stream(declaredMethods)
            .filter(method -> method.isAnnotationPresent(annotationClass))
            .forEach(method -> {
                try {
                    ReflectionHelper.callMethod(invokedObject, method.getName());
                } catch (Exception e) {
                    System.out.println("Method:" + method.getName() + " executed with exception:" + e.getMessage());
                }
            });
    }
}
