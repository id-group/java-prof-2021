package ru.idgroup.otus.bytecode.proxy;

import ru.idgroup.otus.bytecode.annotations.Log;
import ru.idgroup.otus.bytecode.demo.TestLogging;
import ru.idgroup.otus.bytecode.demo.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    public static TestLogging createLoggingClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    private static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {

                //todo без такого преобразования не находит метод с примитивными типами, есть вариант лучше?
                final Class<?>[] classes = Arrays.stream(args).map(o -> {
                    if (o.getClass().getSimpleName().equals("Integer"))
                        return Integer.TYPE;
                    else
                        return o.getClass();
                }).toArray(Class<?>[]::new);
                var declaredMethod = myClass.getClass().getDeclaredMethod(method.getName(), classes);
                if (declaredMethod.isAnnotationPresent(Log.class))
                    System.out.println("executed method: " + declaredMethod.getName() + ", param: " +  Arrays.toString(args));

            } catch (Exception ignored) {
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }

    }
}
