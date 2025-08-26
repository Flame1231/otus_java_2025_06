package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ATMTest {

    private ATM atm;

    @BeforeEach
    void setUp() {
        CellsBlock cellsBlock = CellsBlock.builder()
                .addCell(new CellForMoney(BanknoteType.FIFTY))
                .addCell(new CellForMoney(BanknoteType.ONE_HUNDRED))
                .addCell(new CellForMoney(BanknoteType.TWO_HUNDRED))
                .addCell(new CellForMoney(BanknoteType.FIVE_HUNDRED))
                .addCell(new CellForMoney(BanknoteType.ONE_THOUSAND))
                .build();

        atm = new ATMImpl(cellsBlock);
    }

    @Test
    @DisplayName("Кладем существующие банкноты, проверяем сумму")
    void testDepositSupportedBanknotes() {
        atm.deposit(50);
        atm.deposit(100);
        atm.deposit(500);
        atm.deposit(1000);

        assertEquals(1650, atm.withdrawAll());
    }

    @Test
    @DisplayName("Попытка положить неподдерживаемые банкноты (сумма не изменяется)")
    void testDepositUnsupportedBanknotes() {
        atm.deposit(400);
        atm.deposit(10);

        assertEquals(0, atm.withdrawAll());
    }

    @Test
    @DisplayName("Выдача суммы — успешный сценарий")
    void testWithdrawSuccess() {
        atm.deposit(1000);
        atm.deposit(500);
        atm.withdraw(500);

        assertEquals(1000, atm.withdrawAll());
    }

    @Test
    @DisplayName("Выдача суммы больше, чем есть в банкомате — неуспех")
    void testWithdrawNotEnoughMoney() {
        atm.deposit(100);
        atm.withdraw(500);

        assertEquals(100, atm.withdrawAll());
    }

    @Test
    @DisplayName("Выдача суммы невозможной комбинацией — неуспех")
    void testWithdrawImpossibleCombination() {
        atm.deposit(1000);
        atm.deposit(500);
        atm.withdraw(100); // нельзя выдать 100, минимальная банкнота 50

        assertEquals(1500, atm.withdrawAll());
    }

    @Test
    @DisplayName("Выдача несколькими банкнотами — успех")
    void testWithdrawMultipleNotes() {
        atm.deposit(1000);
        atm.deposit(1000);
        atm.deposit(500);
        atm.withdraw(2000);

        assertEquals(500, atm.withdrawAll());
    }

    @Test
    @DisplayName("Проверка остатка денежных средств после депозитов и частичной выдачи")
    void testGetTotalBalance() {
        atm.deposit(1000);
        atm.deposit(500);
        atm.deposit(50);

        assertEquals(1550, atm.getBalance());

        atm.withdraw(500);
        assertEquals(1050, atm.getBalance());

        atm.withdraw(1000);
        assertEquals(50, atm.getBalance());
    }
}
