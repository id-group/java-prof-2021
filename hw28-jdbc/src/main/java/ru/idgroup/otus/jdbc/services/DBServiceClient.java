package ru.idgroup.otus.jdbc.services;


import ru.idgroup.otus.jdbc.model.Client;
import ru.idgroup.otus.jdbc.model.Phone;

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
