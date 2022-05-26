package ru.idgroup.otus.jdbc.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.idgroup.otus.jdbc.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
