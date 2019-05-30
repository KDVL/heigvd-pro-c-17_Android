/**
 * Model Transaction
 * <i>An exchange or transfer of goods, services, or funds.</i>
 *
 * <p>
 * Defines a transaction with an id, a date, a category, an amount, a type and a description.
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.models;

import java.io.Serializable;

import ch.heig.cashflow.utils.Type;

@SuppressWarnings("serial")
public abstract class Transaction implements Serializable {

    private final long id;
    private String date;
    private Category category;
    private long amount;
    private Type type;
    private String description;

    /**
     * The Transaction constructor
     *
     * @param id          The transaction id
     * @param date        The transaction date
     * @param category    The transaction category
     * @param amount      The transaction amount
     * @param type        The transaction amount
     * @param description The transaction description
     */
    public Transaction(long id, String date, Category category, long amount, Type type, String description) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    /**
     * Get the id of the transaction
     *
     * @return String The transaction id
     */
    public long getID() {
        return id;
    }

    /**
     * Get the date of the transaction
     *
     * @return String The transaction date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the transaction date
     *
     * @param date The transaction date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the category of the transaction
     *
     * @return String The transaction category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set the transaction category
     *
     * @param category The transaction category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get the amount of the transaction
     *
     * @return String The transaction amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Set the transaction amount
     *
     * @param amount The transaction amount
     */
    public void setAmount(float amount) {
        this.amount = new Double(amount * 100).longValue();
    }

    /**
     * Get the description of the transaction
     *
     * @return String The transaction description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the transaction description
     *
     * @param description The transaction description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the type of the transaction
     *
     * @return String The transaction {@code Type}
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the transaction type
     *
     * @param type The transaction {@code Type}
     */
    public void setType(Type type) {
        this.type = type;
    }
}
