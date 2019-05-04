package ch.heig.cashflow.models;

import java.util.Date;

public class Expense {
    private long id;

    private Category category;

    private Date date;

    private long montant;

    private String description;

    public Expense() {

    }

    public Expense(long id, Category category, Date date, long montant, String description) {
        this.id = id;
        this.category = category;
        this.date = date;
        this.montant = montant;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
