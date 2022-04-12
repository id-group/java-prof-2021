package ru.idgroup.otus.webserver.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.idgroup.otus.webserver.crm.service.DBServiceClient;
import ru.idgroup.otus.webserver.helpers.FileSystemHelper;
import ru.idgroup.otus.webserver.services.TemplateProcessor;
import ru.idgroup.otus.webserver.servlet.ClientApiServlet;
import ru.idgroup.otus.webserver.servlet.ClientServlet;


public class ClientsWebServerSimple implements ClientsWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final DBServiceClient dbServiceClient;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;

    public ClientsWebServerSimple(int port, DBServiceClient userDao, Gson gson, TemplateProcessor templateProcessor) {
        this.dbServiceClient = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients", "/addclient", "/api/client/*"));


        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        final ServletHolder servletClient = new ServletHolder(new ClientServlet(templateProcessor, dbServiceClient));
        servletContextHandler.addServlet(servletClient, "/clients");
        servletContextHandler.addServlet(servletClient, "/addclient");

        servletContextHandler.addServlet(new ServletHolder(new ClientApiServlet(dbServiceClient, gson)), "/api/client/*");
        return servletContextHandler;
    }
}
