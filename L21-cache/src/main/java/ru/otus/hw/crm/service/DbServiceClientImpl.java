package ru.otus.hw.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.hw.core.repository.DataTemplate;
import ru.otus.hw.core.sessionmanager.TransactionManager;
import ru.otus.hw.crm.model.Client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, MyCache<String, Client>  cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                cache.put(String.valueOf(clientCloned.getId()), clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            cache.put(String.valueOf(clientCloned.getId()), clientCloned);
            log.info("put client into cache: {}", clientCloned);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var client = cache.get(String.valueOf(id));
            if (Objects.nonNull(client)) {
                log.info("client from cache: {}", client);
                return Optional.of(client);
            }
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            if (clientOptional.isPresent()) {
                client = clientOptional.get();
                cache.put(client.getId().toString(), client);
                return Optional.of(client);
            }
            return Optional.empty();
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
