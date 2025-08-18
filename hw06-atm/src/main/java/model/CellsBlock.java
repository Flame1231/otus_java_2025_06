package model;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Класс представляет блок ячеек банкомата для хранения банкнот разных номиналов.
 * Обеспечивает операции пополнения, выдачи и получения общей суммы денег.
 */
public class CellsBlock {

    /**
     * Набор ячеек с различными номиналами банкнот, отсортированный по возрастанию номинала
     */
    private final NavigableSet<MoneyCell> cells;

    /**
     * Стратегия выдачи денег из ячеек
     */
    private final WithdrawStrategy strategy;

    /**
     * Приватный конструктор. Используется {@link Builder} для создания экземпляра.
     *
     * @param cells    набор ячеек
     * @param strategy стратегия выдачи
     */
    private CellsBlock(Set<MoneyCell> cells, WithdrawStrategy strategy) {
        this.cells = new TreeSet<>(cells);
        this.strategy = strategy;
    }

    /**
     * Создает новый билдера для конфигурации {@link CellsBlock}.
     *
     * @return новый экземпляр {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Пытается положить банкноту в подходящую ячейку.
     *
     * @param banknote номинал банкноты
     * @return true, если банкнота принята, иначе false
     */
    public boolean deposit(int banknote) {
        return cells.stream()
                .filter(c -> c.matches(banknote))
                .findFirst()
                .map(c -> {
                    c.deposit();
                    return true;
                })
                .orElseGet(() -> {
                    log.warn("Не принимаем банкноту {}", banknote);
                    return false;
                });
    }

    /**
     * Пытается выдать указанную сумму, используя стратегию выдачи.
     *
     * @param sum сумма для выдачи
     * @return true, если сумма успешно выдана, иначе false
     */
    public boolean withdraw(int sum) {
        if (!isDivisible(sum)) {
            log.warn("Сумма {} не может быть выдана существующими номиналами", sum);
            return false;
        }

        Map<MoneyCell, Integer> plan = strategy.calculate(cells, sum);
        if (plan.isEmpty()) {
            log.warn("Недостаточно банкнот для выдачи суммы {}", sum);
            return false;
        }

        plan.forEach((cell, count) -> {
            for (int i = 0; i < count; i++) cell.withdraw();
        });

        log.info("Выдана сумма {}", sum);
        return true;
    }

    /**
     * Получает общую сумму денег во всех ячейках блока.
     *
     * @return общая сумма денег
     */
    public int getTotalBalance() {
        return cells.stream().mapToInt(MoneyCell::totalValue).sum();
    }

    /**
     * Выдает всю наличность из всех ячеек.
     *
     * @return сумма, которая была выдана
     */
    public int withdrawAll() {
        int total = cells.stream().mapToInt(MoneyCell::totalValue).sum();
        cells.forEach(cell -> {
            while (cell.withdraw()) {}
        });
        log.info("Выдана вся сумма: {}", total);
        return total;
    }

    /**
     * Проверяет, может ли заданная сумма быть выдана с использованием минимального номинала банкноты.
     *
     * @param sum сумма для проверки
     * @return true, если сумма делится на номинал минимальной банкноты, иначе false
     */
    private boolean isDivisible(int sum) {
        return sum % cells.first().getDenomination() == 0;
    }

    /**
     * Статический вложенный класс для пошагового построения {@link CellsBlock}.
     */
    public static class Builder {
        private final Set<MoneyCell> cells = new HashSet<>();
        private WithdrawStrategy strategy = new GreedyWithdrawStrategy();

        /**
         * Добавляет ячейку для хранения банкнот.
         *
         * @param cell объект {@link MoneyCell} для добавления
         * @return текущий экземпляр билдера
         */
        public Builder addCell(MoneyCell cell) {
            cells.add(cell);
            return this;
        }

        /**
         * Устанавливает стратегию выдачи для блока ячеек.
         *
         * @param strategy стратегия выдачи
         * @return текущий экземпляр билдера
         */
        public Builder setWithdrawStrategy(WithdrawStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        /**
         * Создает экземпляр {@link CellsBlock} с заданными ячейками и стратегией.
         *
         * @return новый {@link CellsBlock}
         * @throws IllegalArgumentException если ни одна ячейка не добавлена
         */
        public CellsBlock build() {
            if (cells.isEmpty()) throw new IllegalArgumentException("Нет ячеек для денег");
            return new CellsBlock(cells, strategy);
        }
    }
}
