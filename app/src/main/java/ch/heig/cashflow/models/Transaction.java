package ch.heig.cashflow.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Transaction implements Serializable {

    public enum Type {
        EXPENSE, INCOME
    }

    private final long ID;
    private String date;
    private Category category;
    private long amount;
    private Type type;
    private String description;

    public Transaction(long id, String date, Category category, long amount, Type type, String description) {
        this.ID = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public long getId() {
        return ID;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
}
