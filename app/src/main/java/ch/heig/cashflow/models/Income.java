/**
 * TODO
 *
 * @author Thibaud ALT
 * @version 1.0
 */
 package ch.heig.cashflow.models;

import java.io.Serializable;

import ch.heig.cashflow.utils.Type;

@SuppressWarnings("serial")
public class Income extends Transaction implements Serializable {

    private final static Type TYPE = Type.INCOME;

    public Income(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
