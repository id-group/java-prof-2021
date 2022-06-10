package ru.idgroup.otus.jdbc.services;

import org.springframework.stereotype.Service;
import ru.idgroup.otus.jdbc.model.Client;
import ru.idgroup.otus.jdbc.model.Phone;
import ru.idgroup.otus.jdbc.repositories.ClientRepository;
import ru.idgroup.otus.jdbc.repositories.PhoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceClientImpl implements DBServiceClient {
    private ClientRepository clientRepository;
    private PhoneRepository phoneRepository;

    public DBServiceClientImpl(ClientRepository clientRepository, PhoneRepository phoneRepository) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
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

    @Override
    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Optional<Phone> getPhone(Long phoneId) {
        return phoneRepository.findById(phoneId);
    }

    @Override
    public Optional<Client> findById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public List<Phone> findClientPhones(Long clientId) {
        return phoneRepository.findPhoneByClientId(clientId);
    }
}
