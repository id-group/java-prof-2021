package ru.idgroup.otus.webserver.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.idgroup.otus.webserver.core.repository.DataTemplate;
import ru.idgroup.otus.webserver.core.sessionmanager.TransactionManager;
import ru.idgroup.otus.webserver.crm.model.Client;
import ru.idgroup.otus.webserver.crm.model.User;

import java.util.Optional;

public class DBServiceUserImpl implements DBServiceUser {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;

    public DBServiceUserImpl(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) {
        this.transactionManager = transactionManager;
        this.userDataTemplate = userDataTemplate;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findByEntityField(session, "login", login).stream().findFirst();
            log.info("user: {}", userOptional);
            return userOptional;
        });
    }

    @Override
    public Optional<User> findById(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findById(session, id);
            log.info("user: {}", userOptional);
            return userOptional;
        });
    }
}
