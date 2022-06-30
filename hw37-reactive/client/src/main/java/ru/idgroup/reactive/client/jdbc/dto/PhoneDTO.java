package ru.idgroup.reactive.client.jdbc.dto;


public class PhoneDTO {
    private Long id;
    private String number;

    public PhoneDTO() {
        number = "";
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
