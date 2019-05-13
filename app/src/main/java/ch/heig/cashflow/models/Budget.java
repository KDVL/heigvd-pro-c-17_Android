package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

public class Budget implements Serializable {

    private final String name;
    private final long expense;
    private final long income;
    private final long budget;
    private final List<BudgetCategory> categories;

    public Budget(String name, long expense, long income, long budget, List<BudgetCategory> categories) {
        this.name = name;
        this.expense = expense;
        this.income = income;
        this.budget = budget;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public long getExpense() {
        return expense;
    }

    public long getIncome() {
        return income;
    }

    public long getBudget() {
        return budget;
    }

    public List<BudgetCategory> getCategories() {
        return categories;
    }
}