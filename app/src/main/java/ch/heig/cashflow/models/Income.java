package ch.heig.cashflow.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Income extends Transaction implements Serializable {

    private final static Type TYPE = Type.INCOME;

    public Income(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
