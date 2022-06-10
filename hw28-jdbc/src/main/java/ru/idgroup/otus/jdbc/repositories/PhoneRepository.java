package ru.idgroup.otus.jdbc.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.idgroup.otus.jdbc.model.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    List<Phone> findPhoneByClientId(Long clientId);
}
