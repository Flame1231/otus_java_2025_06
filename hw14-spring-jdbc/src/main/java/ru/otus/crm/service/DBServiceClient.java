package ru.otus.crm.service;

import java.util.List;
import ru.otus.crm.model.Client;

/**
 * Сервис для работы с сущностями в БД
 */
public interface DBServiceClient {

    Client saveClient(Client object);

    List<Client> findAll();
}
