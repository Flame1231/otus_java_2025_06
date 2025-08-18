package model;

/**
 * Интерфейс для ячейки с деньгами.
 * Определяет минимальный набор операций для работы с купюрами.
 */
public interface MoneyCell extends Comparable<MoneyCell> {

    /** Положить одну банкноту в ячейку */
    void deposit();

    /**
     * Выдать одну банкноту из ячейки
     * @return true, если удалось выдать, false если банкнот нет
     */
    boolean withdraw();

    /** Проверяет, совпадает ли номинал ячейки с указанной суммой */
    boolean matches(int amount);

    /** Получить номинал банкноты */
    int getDenomination();

    /** Общая сумма денег в ячейке */
    int totalValue();

    /** Проверяет, можно ли выдать указанное количество банкнот */
    boolean canProvide(int num);
}
