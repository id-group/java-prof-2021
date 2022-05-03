package ru.idgroup.otus.di.appcontainer;


import ru.idgroup.otus.di.appcontainer.api.AppComponent;
import ru.idgroup.otus.di.appcontainer.api.AppComponentsContainer;
import ru.idgroup.otus.di.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...

        final Object instantiate;
        try {
            instantiate = configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        final Method[] declaredMethods = configClass.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {

                    final Object[] args = Arrays.stream(method.getParameterTypes())
                            .map(this::getAppComponent)
                            .toList()
                            .toArray();
                    Object beanObject = this.invokeBean(instantiate, method, args);

                    final AppComponent annotation = method.getAnnotation(AppComponent.class);
                    appComponents.add(beanObject);
                    appComponentsByName.put(annotation.name(), beanObject);
                });
    }

    private Object invokeBean(Object object, Method method, Object ... args ) {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        final Object o1 = appComponents.stream()
                .filter(o -> o.getClass().equals(componentClass) || List.of(o.getClass().getInterfaces()).contains(componentClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Класс " + componentClass.getName() + " не найден."));
        return (C) o1;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
