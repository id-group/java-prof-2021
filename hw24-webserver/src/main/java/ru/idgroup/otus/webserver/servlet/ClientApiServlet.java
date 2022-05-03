package ru.idgroup.otus.webserver.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.StringUtil;
import ru.idgroup.otus.webserver.crm.model.Address;
import ru.idgroup.otus.webserver.crm.model.Client;
import ru.idgroup.otus.webserver.crm.model.Phone;
import ru.idgroup.otus.webserver.crm.model.User;
import ru.idgroup.otus.webserver.crm.service.DBServiceClient;
import ru.idgroup.otus.webserver.crm.service.DBServiceUser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if(!StringUtil.isBlank(name)) {
            String address = req.getParameter("address");
            String[] phones = req.getParameterValues("phone");

            final List<Phone> phoneList = Arrays.stream(phones).map(s -> new Phone(null, s)).collect(Collectors.toList());
            var client = new Client(null, name, new Address(null, address), phoneList);
            dbServiceClient.saveClient(client);
        }
        resp.sendRedirect("/clients");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.getClient(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
