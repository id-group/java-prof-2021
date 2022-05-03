package ru.idgroup.otus.webserver.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idgroup.otus.webserver.crm.service.DBServiceClient;
import ru.idgroup.otus.webserver.dto.ClientDTO;
import ru.idgroup.otus.webserver.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ClientServlet extends HttpServlet {

    private static final String CLIENT_PAGE_TEMPLATE = "clients.html";
    private static final String ADD_CLIENT_FORM_TEMPLATE = "addclient.html";
    private static final String TEMPLATE_CLIENT = "clients";

    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        switch (req.getRequestURI()) {
            case "/clients" : getClients(response);break;
            case "/addclient" : getAddClientForm(response);break;
        }

    }

    private void getAddClientForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_CLIENT_FORM_TEMPLATE, null));
    }

    private void getClients(HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        final List<ClientDTO> clients = dbServiceClient.findAll().stream()
                        .map(client -> new ClientDTO(client.getId(), client.getName(), client.getAddress(), client.getPhones()))
                        .collect(Collectors.toList());
        paramsMap.put(TEMPLATE_CLIENT, clients);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENT_PAGE_TEMPLATE, paramsMap));
    }

}
