package ru.idgroup.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sb = new StringBuilder()
                .append("select * from ")
                .append(entityClassMetaData.getName());
        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sb = new StringBuilder()
                .append("select ");

        sb.append(entityClassMetaData.getAllFields().stream()
                .map(o -> {
                    Field f = (Field) o;
                    return f.getName();
                })
                .collect(Collectors.joining(",")));


        sb.append(" from ")
            .append(entityClassMetaData.getName())
            .append(" where ")
            .append(entityClassMetaData.getIdField().getName())
            .append(" = ?");

        return sb.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder()
                .append("insert into ")
                .append(entityClassMetaData.getName())
                .append("(");

        sb.append(entityClassMetaData.getFieldsWithoutId().stream()
                .map(o -> {
                    Field f = (Field) o;
                    return f.getName();
                })
                .collect(Collectors.joining(",")));


        sb.append(") values (");
        sb.append(entityClassMetaData.getFieldsWithoutId().stream().map(o -> "?").collect(Collectors.joining(",")));
        sb.append(")");

        return sb.toString();    }

    @Override
    public String getUpdateSql() {
        StringBuilder sb = new StringBuilder()
                .append("update ")
                .append(entityClassMetaData.getName())
                .append(" set ");

        sb.append(entityClassMetaData.getFieldsWithoutId().stream()
                .map(o -> {
                    Field f = (Field) o;
                    return f.getName() + " = ?";
                })
                .collect(Collectors.joining(",")));


        sb.append(" where ")
            .append(entityClassMetaData.getIdField().getName())
            .append(" = ?");

        return sb.toString();
    }
}
