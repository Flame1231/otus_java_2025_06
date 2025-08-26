package model;

public enum BanknoteType {
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    private final int amount;

    BanknoteType(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
