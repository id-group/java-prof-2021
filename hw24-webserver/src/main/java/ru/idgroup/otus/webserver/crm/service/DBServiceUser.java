package ru.idgroup.otus.webserver.crm.service;

import ru.idgroup.otus.webserver.crm.model.User;

import java.util.Optional;

public interface DBServiceUser {
    Optional<User> findByLogin(String login);
    Optional<User> findById(long id);
}
