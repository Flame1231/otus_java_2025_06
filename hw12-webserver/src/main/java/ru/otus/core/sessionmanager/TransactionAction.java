package ru.otus.core.sessionmanager;

import java.util.function.Function;
import org.hibernate.Session;

/**
 * Тарнзакция
 */
public interface TransactionAction<T> extends Function<Session, T> {}
