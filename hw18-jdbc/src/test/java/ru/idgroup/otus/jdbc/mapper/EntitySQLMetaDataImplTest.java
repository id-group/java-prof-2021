package ru.idgroup.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.idgroup.otus.jdbc.crm.model.Client;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EntitySQLMetaDataImplTest {
    EntityClassMetaData entity;
    EntitySQLMetaDataImpl sqlMetaData;

    @BeforeEach
    void setUp() {
        entity = new EntityClassMetaDataImpl<>(Client.class);
        sqlMetaData = new EntitySQLMetaDataImpl(entity);
    }

    @Test
    void getSelectAllSql() {
        //given
        String selectAll = "select * from client";

        //when
        var resSelectAll = sqlMetaData.getSelectAllSql();

        //then
        assertThat(resSelectAll).isEqualTo(selectAll);
    }

    @Test
    void getSelectByIdSql() {
        //given
        String selectById = "select id,name from client where id = ?";

        //when
        var resultSelect = sqlMetaData.getSelectByIdSql();

        //then
        assertThat(resultSelect).isEqualTo(selectById);
    }

    @Test
    void getInsertSql() {
        //given
        String insert = "insert into client(name) values (?)";

        //when
        var resultInsert = sqlMetaData.getInsertSql();

        //then
        assertThat(resultInsert).isEqualTo(insert);
    }

    @Test
    void getUpdateSql() {
        //given
        String update = "update client set name = ? where id = ?";

        //when
        var resultUpdate = sqlMetaData.getUpdateSql();

        //then
        assertThat(resultUpdate).isEqualTo(update);
    }
}
