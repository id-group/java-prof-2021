package ru.idgroup.otus.annotations.usage;

import ru.idgroup.otus.annotations.reflection.AnnotationInvoker;

import java.lang.reflect.InvocationTargetException;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AnnotationInvoker.execute("ru.idgroup.otus.annotations.usage.AnnotationTestClass");
    }
}
