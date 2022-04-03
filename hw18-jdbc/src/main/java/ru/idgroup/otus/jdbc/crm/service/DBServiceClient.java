package ru.idgroup.otus.jdbc.crm.service;

import ru.idgroup.otus.jdbc.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
