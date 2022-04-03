package ru.idgroup.otus.jdbc.mapper;

import ru.idgroup.otus.jdbc.core.repository.DataTemplate;
import ru.idgroup.otus.jdbc.core.repository.DataTemplateException;
import ru.idgroup.otus.jdbc.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private EntityClassMetaData entity;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntityClassMetaData entity, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entity = entity;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return this.createEntity(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var resultList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    var item = this.createEntity(rs);
                    resultList.add(item);
                }
                return resultList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T item) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getFields(item));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T item) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    getFieldsWithId(item));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createEntity(ResultSet rs) {
        List<Object> args = (List) entity.getAllFields().stream()
                .map( o -> {
                    Field f = (Field) o;
                    try {
                        return rs.getObject(f.getName(), f.getType());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException("Несовместимые типы данных.");
                    }
                }).collect(Collectors.toList());

        try {

            return (T) entity.getConstructor().newInstance(args.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private List<Object> getFields(T item) {
        return (List) entity.getFieldsWithoutId().stream()
                .map( o -> {
                    try {
                        Field f = (Field) o;
                        f.setAccessible(true);
                        return f.get(item);
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                }).collect(Collectors.toList());

    }

    private List<Object> getFieldsWithId(T item) {
        try {
            List<Object> list = this.getFields(item);
            Field f = entity.getIdField();
            f.setAccessible(true);
            Object idValue = f.get(item);
            list.add(idValue);
            return list;
        } catch (IllegalAccessException e) {
            throw  new IllegalArgumentException(e.getMessage());
        }
    }

}
