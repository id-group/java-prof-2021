package ru.idgroup.reactive.source.dto;



import lombok.AllArgsConstructor;
import ru.idgroup.reactive.source.model.Address;
import ru.idgroup.reactive.source.model.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String name;
    private String address;

    private List<PhoneDTO> phones = new ArrayList<>();

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public ClientDTO(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address != null ? address.getStreet() : "";
        this.phones.addAll( phones.stream().map(phone -> new PhoneDTO(phone.getId(), phone.getNumber())).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }
}
