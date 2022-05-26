package ru.idgroup.otus.jdbc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class Address {
    @Id
    private final Long id;

    private final String street;

    private final Long client_id;

    @PersistenceConstructor
    public Address(Long id, String street, Long client_id) {
        this.id = id;
        this.street = street;
        this.client_id = client_id;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public Long getClient_id() {
        return client_id;
    }
}
