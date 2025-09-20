package ru.otus.jdbc.mapper.interfac;

/**
 * Генерация SQL-запросов для класса
 */
public interface EntitySQLMetaData {

    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();
}
