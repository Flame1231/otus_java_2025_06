package model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Класс представляет ячейку для хранения банкнот одного номинала в банкомате.
 * Реализует интерфейс {@link MoneyCell} и обеспечивает операции пополнения,
 * выдачи и проверки возможности выдачи.
 */
public class CellForMoney implements MoneyCell {

    /**
     * Тип банкноты, хранящейся в ячейке
     */
    private final BanknoteType banknoteType;

    /**
     * Количество банкнот в ячейке
     */
    private int count = 0;

    /**
     * Создает ячейку для конкретного номинала банкнот.
     *
     * @param banknoteType тип банкноты, которая будет храниться в ячейке
     */
    public CellForMoney(BanknoteType banknoteType) {
        this.banknoteType = banknoteType;
    }

    /**
     * Добавляет одну банкноту в ячейку.
     * Увеличивает счетчик и выводит debug-информацию.
     */
    @Override
    public void deposit() {
        count++;
        log.debug("Добавлена банкнота {} (всего: {})", banknoteType.getAmount(), count);
    }

    /**
     * Пытается выдать одну банкноту из ячейки.
     *
     * @return true, если банкнота была выдана; false, если банкнот нет
     */
    @Override
    public boolean withdraw() {
        if (count > 0) {
            count--;
            log.debug("Выдана банкнота {} (осталось: {})", banknoteType.getAmount(), count);
            return true;
        }
        log.warn("Нет купюр номиналом {}", banknoteType.getAmount());
        return false;
    }

    /**
     * Проверяет, соответствует ли банкнота заданной сумме.
     *
     * @param amount сумма для проверки
     * @return true, если номинал банкноты совпадает с amount, иначе false
     */
    @Override
    public boolean matches(int amount) {
        return banknoteType.getAmount() == amount;
    }

    /**
     * Возвращает номинал банкноты, хранящейся в ячейке.
     *
     * @return номинал банкноты
     */
    @Override
    public int getDenomination() {
        return banknoteType.getAmount();
    }

    /**
     * Возвращает общую сумму денег в ячейке.
     *
     * @return произведение количества банкнот на их номинал
     */
    @Override
    public int totalValue() {
        return count * banknoteType.getAmount();
    }

    /**
     * Проверяет, может ли ячейка выдать заданное количество банкнот.
     *
     * @param num количество банкнот для проверки
     * @return true, если в ячейке есть хотя бы num банкнот
     */
    @Override
    public boolean canProvide(int num) {
        return count >= num;
    }

    /**
     * Сравнивает текущую ячейку с другой по номиналу банкнот.
     *
     * @param other другая ячейка
     * @return отрицательное число, ноль или положительное число
     * в зависимости от сравнения номиналов
     */
    @Override
    public int compareTo(MoneyCell other) {
        return Integer.compare(this.banknoteType.getAmount(), other.getDenomination());
    }
}
