/**
 * TODO
 *
 * @author Thibaud ALT
 * @version 1.0
 */
 package ch.heig.cashflow.models;

import ch.heig.cashflow.utils.Type;

public class PostTransaction {

    private String date;
    private long categoryId;
    private long amount;
    private Type type;
    private String description;

    public PostTransaction(Transaction transaction) {
        this.date = transaction.getDate();
        this.categoryId = transaction.getCategory().getID();
        this.amount = transaction.getAmountLong();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
    }
}
