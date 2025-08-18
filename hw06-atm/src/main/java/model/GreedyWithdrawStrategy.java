package model;

import java.util.*;

public class GreedyWithdrawStrategy implements WithdrawStrategy {

    @Override
    public Map<MoneyCell, Integer> calculate(Set<MoneyCell> cells, int sum) {
        Map<MoneyCell, Integer> plan = new HashMap<>();
        int remaining = sum;

        NavigableSet<MoneyCell> sortedCells = new TreeSet<>(cells).descendingSet();

        for (MoneyCell cell : sortedCells) {
            int denom = cell.getDenomination();
            int needed = remaining / denom;
            int available = Math.min(needed, cell.totalValue() / denom);

            if (available > 0) {
                plan.put(cell, available);
                remaining -= available * denom;
            }
        }

        return remaining == 0 ? plan : Map.of();
    }
}
