package ru.idgroup.otus.jdbc.services;

import org.springframework.stereotype.Service;
import ru.idgroup.otus.jdbc.model.Client;
import ru.idgroup.otus.jdbc.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceClientImpl implements DBServiceClient {
    private ClientRepository clientRepository;

    public DBServiceClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }
}
