package ru.idgroup.otus.webserver.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
