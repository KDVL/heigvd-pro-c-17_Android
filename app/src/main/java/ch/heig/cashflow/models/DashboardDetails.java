package ch.heig.cashflow.models;

public class DashboardDetails {

    private final long ID;
    private final String name;
    private final long expense;
    private final long income;
    private final long budget;
    private String iconName;

    public DashboardDetails(long id, String name, long expense, long income, long budget) {
        this.ID = id;
        this.name = name;
        this.expense = expense;
        this.income = income;
        this.budget = budget;
    }

    public long getID() {
        return ID;
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

    public String getIconName() {
        return iconName;
    }
}
