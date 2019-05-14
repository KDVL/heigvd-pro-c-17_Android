package ch.heig.cashflow.models;

public class BudgetCategory {

    private final long expense;
    private final long income;
    private final long budget;
    private final Category category;

    public BudgetCategory(long expense, long income, long budget, Category category) {
        this.expense = expense;
        this.income = income;
        this.budget = budget;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }
}
