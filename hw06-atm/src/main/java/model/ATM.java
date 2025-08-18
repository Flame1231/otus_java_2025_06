package model;

/**
 * Интерфейс банкомата.
 */
public interface ATM {

    /**
     * Положить банкноту в банкомат
     */
    boolean deposit(int banknote);

    /**
     * Выдать указанную сумму
     */
    boolean withdraw(int sum);

    /**
     * Выдать все деньги
     */
    int withdrawAll();

    /**
     * Получить текущий остаток денежных средств в банкомате
     */
    int getBalance();
}
