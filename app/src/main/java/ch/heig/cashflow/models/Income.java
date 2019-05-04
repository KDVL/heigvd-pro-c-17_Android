package ch.heig.cashflow.models;

public class Income extends Transaction {

    private final static Type TYPE = Type.INCOME;

    public Income(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
