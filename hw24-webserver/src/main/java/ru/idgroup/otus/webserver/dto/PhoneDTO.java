package ru.idgroup.otus.webserver.dto;

import javax.persistence.Column;

public class PhoneDTO {
    private Long id;
    private String number;

    public PhoneDTO(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
}
