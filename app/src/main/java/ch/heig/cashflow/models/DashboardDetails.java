package ch.heig.cashflow.models;

class DashboardDetails {

    private String name;
    private long expense;
    private long income;
    private long budget;

    public DashboardDetails(String name, long expense, long income, long budget) {
        this.name = name;
        this.expense = expense;
        this.income = income;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpense() {
        return expense;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }
}
