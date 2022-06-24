package ru.idgroup.reactive.source.dto;


public class PhoneDTO {
    private Long id;
    private String number;

    public PhoneDTO() {
    }

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
