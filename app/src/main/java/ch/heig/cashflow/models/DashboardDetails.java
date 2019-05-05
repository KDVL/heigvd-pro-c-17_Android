package ch.heig.cashflow.models;

class DashboardDetails {

    private long expense;
    private long income;
    private long budget;

    public DashboardDetails(long expense, long income, long budget) {
        this.expense = expense;
        this.income = income;
        this.budget = budget;
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
