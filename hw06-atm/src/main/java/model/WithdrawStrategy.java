package model;

import java.util.Map;
import java.util.Set;

/**
 * Стратегия выдачи денег из набора ячеек.
 */
public interface WithdrawStrategy {

    /**
     * Рассчитать план выдачи суммы
     * @param cells набор доступных ячеек
     * @param sum требуемая сумма
     * @return карта: ячейка -> количество банкнот
     */
    Map<MoneyCell, Integer> calculate(Set<MoneyCell> cells, int sum);
}
