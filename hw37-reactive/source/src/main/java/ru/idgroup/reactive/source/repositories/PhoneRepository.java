package ru.idgroup.reactive.source.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.idgroup.reactive.source.model.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    List<Phone> findPhoneByClientId(Long clientId);
}
