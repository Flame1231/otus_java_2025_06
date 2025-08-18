package model;

public class ATMImpl implements ATM {
    private final CellsBlock cellsBlock;

    public ATMImpl(CellsBlock cellsBlock) {
        this.cellsBlock = cellsBlock;
    }

    @Override
    public boolean deposit(int banknote) {
        return cellsBlock.deposit(banknote);
    }

    @Override
    public boolean withdraw(int sum) {
        return cellsBlock.withdraw(sum);
    }

    @Override
    public int withdrawAll() {
        return cellsBlock.withdrawAll();
    }

    @Override
    public int getBalance() {
        return cellsBlock.getTotalBalance();
    }
}
