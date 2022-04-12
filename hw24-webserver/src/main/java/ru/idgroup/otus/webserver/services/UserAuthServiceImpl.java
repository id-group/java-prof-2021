package ru.idgroup.otus.webserver.services;

import ru.idgroup.otus.webserver.crm.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private DBServiceUser userDao;

    public UserAuthServiceImpl(DBServiceUser userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return userDao.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
