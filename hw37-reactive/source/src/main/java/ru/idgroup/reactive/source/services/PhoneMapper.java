package ru.idgroup.reactive.source.services;

import ru.idgroup.reactive.source.dto.ClientDTO;
import ru.idgroup.reactive.source.dto.PhoneDTO;
import ru.idgroup.reactive.source.model.Address;
import ru.idgroup.reactive.source.model.Client;
import ru.idgroup.reactive.source.model.Phone;

import java.util.List;
import java.util.stream.Collectors;

public class PhoneMapper {
    public static PhoneDTO toDTO(Phone phone) {
        return new PhoneDTO(phone.getId(), phone.getNumber());
    }

}
