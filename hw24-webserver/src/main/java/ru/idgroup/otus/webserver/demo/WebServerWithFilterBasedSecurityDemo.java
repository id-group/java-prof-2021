package ru.idgroup.otus.webserver.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.idgroup.otus.webserver.cachehw.MyCache;
import ru.idgroup.otus.webserver.core.repository.DataTemplateHibernate;
import ru.idgroup.otus.webserver.crm.model.Client;
import ru.idgroup.otus.webserver.crm.model.User;
import ru.idgroup.otus.webserver.crm.service.DBServiceUserImpl;
import ru.idgroup.otus.webserver.crm.service.DbServiceClientImpl;
import ru.idgroup.otus.webserver.helpers.HibernateHelper;
import ru.idgroup.otus.webserver.server.ClientsWebServer;
import ru.idgroup.otus.webserver.server.ClientsWebServerWithFilterBasedSecurity;
import ru.idgroup.otus.webserver.services.TemplateProcessor;
import ru.idgroup.otus.webserver.services.TemplateProcessorImpl;
import ru.idgroup.otus.webserver.services.UserAuthService;
import ru.idgroup.otus.webserver.services.UserAuthServiceImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8081;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        var transactionManager = HibernateHelper.transationManager();

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        var userTemplate = new DataTemplateHibernate<>(User.class);
        var dbServiceUser = new DBServiceUserImpl(transactionManager, userTemplate);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, new MyCache<Long, Client>());
        ClientsWebServer clientsWebServer = new ClientsWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
