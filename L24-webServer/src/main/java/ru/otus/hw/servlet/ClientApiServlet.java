package ru.otus.hw.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.hw.crm.model.Client;
import ru.otus.hw.crm.service.DBServiceClient;

import java.io.IOException;

public class ClientApiServlet extends HttpServlet {
    private final DBServiceClient serviceClient;

    public ClientApiServlet(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(request.getInputStream(),Client.class);
        serviceClient.saveClient(client);
        response.sendRedirect("/clients");
    }
}
