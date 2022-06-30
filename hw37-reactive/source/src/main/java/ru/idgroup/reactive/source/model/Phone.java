package ru.idgroup.reactive.source.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("phone")
public class Phone {
    @Id
    private final Long id;

    @Column("number")
    private final String number;

    @Column("client_id")
    @NonNull
    private final Long clientId;

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

    @NonNull
    public Long getClientId() {
        return clientId;
    }
}
