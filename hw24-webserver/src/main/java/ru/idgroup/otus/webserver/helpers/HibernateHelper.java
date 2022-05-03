package ru.idgroup.otus.webserver.helpers;

import org.hibernate.cfg.Configuration;
import ru.idgroup.otus.webserver.core.repository.HibernateUtils;
import ru.idgroup.otus.webserver.core.sessionmanager.TransactionManager;
import ru.idgroup.otus.webserver.core.sessionmanager.TransactionManagerHibernate;
import ru.idgroup.otus.webserver.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.idgroup.otus.webserver.crm.model.Address;
import ru.idgroup.otus.webserver.crm.model.Client;
import ru.idgroup.otus.webserver.crm.model.Phone;
import ru.idgroup.otus.webserver.crm.model.User;

public class HibernateHelper {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static TransactionManager transationManager() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class, User.class);

        return new TransactionManagerHibernate(sessionFactory);
    }
}
