package ru.otus;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.util.resource.PathResourceFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.cfg.Configuration;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.hw.core.repository.HibernateUtils;
import ru.otus.hw.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.hw.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.hw.crm.model.Address;
import ru.otus.hw.crm.model.Client;
import ru.otus.hw.crm.model.Phone;
import ru.otus.hw.crm.service.DBServiceClient;
import ru.otus.hw.crm.service.DbServiceClientImpl;
import ru.otus.hw.server.UsersWebServerWithBasicSecurity;
import ru.otus.server.UsersWebServer;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.hw.core.repository.DataTemplateHibernate;

import java.net.URI;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница клиентов
    http://localhost:8080/api/clients

*/
public class WebServerApp {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        PathResourceFactory pathResourceFactory = new PathResourceFactory();
        Resource configResource = pathResourceFactory.newResource(URI.create(hashLoginServiceConfigPath));

        LoginService loginService = new HashLoginService(REALM_NAME, configResource);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, templateProcessor, initializer());

        usersWebServer.start();
        usersWebServer.join();
    }

    private static DBServiceClient initializer()
    {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils
                .buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        return new DbServiceClientImpl(new TransactionManagerHibernate(sessionFactory), new DataTemplateHibernate<>(Client.class));
    }
}
