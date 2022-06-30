package ru.idgroup.reactive.source.repositories;


import org.springframework.data.repository.CrudRepository;
import ru.idgroup.reactive.source.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
