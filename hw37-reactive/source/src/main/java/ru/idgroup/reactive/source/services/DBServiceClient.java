package ru.idgroup.reactive.source.services;


import ru.idgroup.reactive.source.dto.ClientDTO;
import ru.idgroup.reactive.source.model.Client;
import ru.idgroup.reactive.source.model.Phone;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    Phone savePhone(Phone phone);

    Optional<Phone> getPhone(Long phoneId);

    Optional<Client> findById(Long clientId);

    List<Phone> findClientPhones(Long clientId);

}
