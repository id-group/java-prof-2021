package ru.idgroup.otus.jdbc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone {
    @Id
    private final Long id;

    @Column("number")
    private final String number;

    @Column("client_id")
    private Long clientId;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @PersistenceConstructor
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

}
