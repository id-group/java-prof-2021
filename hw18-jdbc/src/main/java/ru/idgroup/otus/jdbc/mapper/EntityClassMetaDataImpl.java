package ru.idgroup.otus.jdbc.mapper;

import ru.idgroup.otus.jdbc.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> domainClass;
    private List<Field> fields;
    private Field id;
    private Constructor<T> declaredConstructor;

    public EntityClassMetaDataImpl(Class<T> domainClass){
        this.domainClass = domainClass;
        classParsing();
    }

    void classParsing() {
        fields = Arrays.stream(domainClass.getDeclaredFields()).toList();
        id = fields.stream()
            .filter(
                field -> field.isAnnotationPresent(Id.class)
            )
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Добавьте @Id для класса " + getName()));

        try {
            var classes = fields.stream().map(field -> field.getAnnotatedType().getType()).toArray(Class<?>[]::new);
            declaredConstructor = domainClass.getDeclaredConstructor(classes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Класс должен включать конструктор со всеми полями.");
        }

    }

    @Override
    public String getName() {
        return domainClass.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return declaredConstructor;
    }

    @Override
    public Field getIdField() {
        return id;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fields.stream().filter( field -> field != id ).collect(Collectors.toList());
    }
}
