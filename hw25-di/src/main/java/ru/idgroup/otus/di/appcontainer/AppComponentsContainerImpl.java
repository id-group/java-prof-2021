package ru.idgroup.otus.di.appcontainer;


import ru.idgroup.otus.di.appcontainer.api.AppComponent;
import ru.idgroup.otus.di.appcontainer.api.AppComponentsContainer;
import ru.idgroup.otus.di.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        final Object instantiate = createInstance(configClass);

        final Method[] declaredMethods = configClass.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    addComponent(instantiate, method);
                });
    }

    private void addComponent(Object instantiate, Method method) {
        final Object[] args = Arrays.stream(method.getParameterTypes())
                .map(this::getAppComponent)
                .toArray();
        Object beanObject = this.invokeBean(instantiate, method, args);

        final AppComponent annotation = method.getAnnotation(AppComponent.class);

        if( appComponentsByName.containsKey(annotation.name()) )
            throw new RuntimeException(annotation.name() + " имя компонента должно быть уникальным.");
        appComponents.add(beanObject);
        appComponentsByName.put(annotation.name(), beanObject);
    }

    private Object createInstance(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeBean(Object object, Method method, Object ... args ) {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
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
                .filter(o -> componentClass.isAssignableFrom(o.getClass()) )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Класс " + componentClass.getName() + " не найден."));
        return (C) o1;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if ( !appComponentsByName.containsKey(componentName) )
            throw new NoSuchElementException("Класс " + componentName + " не найден.");
        return (C) appComponentsByName.get(componentName);
    }
}
