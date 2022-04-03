package ru.idgroup.otus.jdbc.mapper;

import ru.idgroup.otus.jdbc.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> domainClass;
    private Field[] fields;

    public EntityClassMetaDataImpl(Class<T> domainClass){
        this.domainClass = domainClass;
    }

    void classParsing() {
        fields = domainClass.getFields();
        Arrays.stream(fields).filter(
            field -> field.isAnnotationPresent(Id.class)
        )
        .findFirst().orElseThrow(() -> new )

    }

    @Override
    public String getName() {
        return domainClass.getName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return null;
    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(fields).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return null;
    }
}
