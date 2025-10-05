package ru.otus.helpers;

import java.util.List;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DbServiceClient;
import ru.otus.crm.service.DbServiceUser;

/**
 * Хелпер для заполнения базы данных
 */
public final class FillDataBaseHelper {

    /**
     * Заполняем таблицу клиентов
     * @param dbServiceClient - клиент для работы с клиентами
     */
    public static void insertClients(DbServiceClient dbServiceClient) {
        if (dbServiceClient.findAll().isEmpty()) {
            dbServiceClient.save(new Client("Сlient1"));
            dbServiceClient.save(new Client("Сlient2", new Address("Cenrtal")));
            dbServiceClient.save(new Client("Сlient3", List.of(new Phone("82225678899"))));
            dbServiceClient.save(
                    new Client("Сlient4", List.of(new Phone("81545672899"), new Phone("81434567890"))));
            dbServiceClient.save(new Client(
                    "Сlient5", new Address("Street"), List.of(new Phone("84645678898"), new Phone("81434567890"))));
        }
    }

    /**
     * Заполняем таблицу юзеров
     * @param dbServiceUser - клиент для работы с юзерами
     */
    public static void insertUsers(DbServiceUser dbServiceUser) {
        if (dbServiceUser.findAll().isEmpty()) {
            dbServiceUser.save(new User("firstClient", "login1", "123"));
            dbServiceUser.save(new User("firstClient", "login2", "123"));
            dbServiceUser.save(new User("firstClient", "login3", "123"));
        }
    }
}
