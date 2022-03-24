package ru.idgroup.otus.bytecode.proxy;

import org.jetbrains.annotations.NotNull;
import ru.idgroup.otus.bytecode.annotations.Log;
import ru.idgroup.otus.bytecode.demo.TestLogging;
import ru.idgroup.otus.bytecode.demo.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ioc {

    private Ioc() {
    }

    public static TestLogging createLoggingClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(
                TestLoggingImpl.class.getClassLoader(),
                TestLoggingImpl.class.getInterfaces(),
                handler);
    }

    private static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;
        private final Map<String, List<String>> collect;

        DemoInvocationHandler(@NotNull TestLogging myClass) {
            this.myClass = myClass;
            collect = Arrays.stream(myClass.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.groupingBy(Method::getName,
                            Collectors.mapping(method -> Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(",")),
                            Collectors.toList())));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if (collect.containsKey(method.getName())) {
                    List<String> params = collect.get(method.getName());
                    final String types = Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(","));
                    if(params.contains(types))
                        System.out.println("executed method: " + method.getName() + ", param: " +  Arrays.toString(args));
                }
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
