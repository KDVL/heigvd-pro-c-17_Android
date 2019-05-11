package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

public class Dashboard extends Budget implements Serializable {

    private final String name;
    private final List<Budget> budgets;

    public Dashboard(String name, long expense, long income, long budget, List<Budget> budgets) {
        super(expense, income, budget, null);
        this.name = name;
        this.budgets = budgets;
    }

    public String getName() {
        return name;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }
}