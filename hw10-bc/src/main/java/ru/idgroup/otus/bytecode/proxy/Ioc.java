package ru.idgroup.otus.bytecode.proxy;

import org.jetbrains.annotations.NotNull;
import ru.idgroup.otus.bytecode.annotations.Log;
import ru.idgroup.otus.bytecode.demo.TestLogging;
import ru.idgroup.otus.bytecode.demo.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
            final Method[] declaredMethods = myClass.getClass().getDeclaredMethods();
            Arrays.stream(declaredMethods)
                .filter(method1 -> method1.getName().equals(method.getName()) && method1.isAnnotationPresent(Log.class))
                .filter(method1 -> {
                    final Parameter[] parameters = method1.getParameters();
                    if (parameters.length != args.length)
                        return false;

                    Class<?>[] parameter_types = method1.getParameterTypes();
                    for( int i=0;i< parameters.length;i++ ) {
                        if (!parameter_types[0].equals(int.class))
                            return false;
                    }
                    return true;
                })
                .findFirst()
                .ifPresent(method1 -> System.out.println("executed method: " + method1.getName() + ", param: " +  Arrays.toString(args)));
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
