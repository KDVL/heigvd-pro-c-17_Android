package ch.heig.cashflow.models;

public class Expense extends Transaction {

    private final static Type TYPE = Type.EXPENSE;

    public Expense(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
