package ch.heig.cashflow.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Transaction implements Serializable {

    private final long id;
    private String date;
    private Category category;
    private long amount;
    private Type type;
    private String description;
    public Transaction(long id, String date, Category category, long amount, Type type, String description) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public long getID() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getAmountLong() {
        return amount;
    }

    public float getAmountFloat() {
        return amount / 100;
    }


    public void setAmount(long amount) {
        this.amount = amount;
    }
    public void setAmount(float amount) {
        this.amount = new Double(amount * 100).longValue();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        EXPENSE, INCOME
    }

    // Decorator used to POST/PUT data

}
