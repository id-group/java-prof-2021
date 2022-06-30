package ru.idgroup.reactive.source.services;

import ru.idgroup.reactive.source.dto.ClientDTO;
import ru.idgroup.reactive.source.model.Address;
import ru.idgroup.reactive.source.model.Client;
import ru.idgroup.reactive.source.model.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {
    public static ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getAddress(), client.getPhones().stream().toList());
    }

    public static Client updateClient(Client client, ClientDTO clientDTO) {
        Address address = new Address( client.getAddress().getId(), clientDTO.getAddress(), client.getId());
     //   return new Client(client.getId(), clientDTO.getName(), address, client.getPhones());
        return new Client(client.getId(), clientDTO.getName(), address, client.getPhones());
    }
}
