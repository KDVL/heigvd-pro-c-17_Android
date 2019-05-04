package ch.heig.cashflow.models;

import java.util.Date;

public class Expense {
    private long id;

    private Category category;

    private String date;

    private long amount;

    private String description;

    public Expense() {

    }

    public Expense(long id, Category category, String date, long amount, String description) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
