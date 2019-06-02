/**
 * Model PostTransaction
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

    /**
     * The PostTransaction constructor
     *
     * @param transaction The post transaction
     */
    public PostTransaction(Transaction transaction) {
        this.date = transaction.getDate();
        this.categoryId = transaction.getCategory().getID();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
    }
}
