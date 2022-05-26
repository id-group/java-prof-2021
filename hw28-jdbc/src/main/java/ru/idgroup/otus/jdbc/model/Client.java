package ru.idgroup.otus.jdbc.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table("client")
public class Client implements Cloneable {

    @Id
    private final Long id;

    private final String name;

    @Column("client_id")
    private Address address;

    @MappedCollection(idColumn = "id", keyColumn = "number")
    private List<Phone> phones;



    public Client(String name) {
        this(name, "");
    }

    public Client(String name, String address) {
        this(null, name, new Address(null, address, null), null);
    }

    @PersistenceConstructor
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;

        if(phones != null)
            this.phones = phones.stream()
                    .map(phone -> new Phone(phone.getId(), phone.getNumber(), this.getId()))
                    .collect(Collectors.toList());
        else
            this.phones = new ArrayList<>();
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
