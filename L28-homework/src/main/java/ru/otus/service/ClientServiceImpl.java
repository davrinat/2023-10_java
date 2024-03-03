package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            Client savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var clients = clientRepository.findAll();
        log.info("clientList:{}", clients);
        return clients;
    }

    @Override
    public Client findById(long id) {
        var client = clientRepository.findById(id);
        log.info("client:{}", client);
        return client.orElse(null);
    }
}
