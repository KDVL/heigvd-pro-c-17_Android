/**
 * Model Budget
 * <i>A usually leather pouch, wallet, or pack.</i>
 *
 * <p>
 * Defines a budget with a name, an expense amount, an income amount, a budget amount
 * and a list of categories.
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.models;

import java.io.Serializable;
import java.util.List;

public class Budget implements Serializable {

    private final String name;
    private final long expense;
    private final long income;
    private final long budget;
    private final List<BudgetCategory> categories;

    /**
     * The Budget constructor
     *
     * @param name       The budget name
     * @param expense    The budget expense amount
     * @param income     The budget income amount
     * @param budget     The budget amount
     * @param categories The budget categories list
     */
    public Budget(String name, long expense, long income, long budget, List<BudgetCategory> categories) {
        this.name = name;
        this.expense = expense;
        this.income = income;
        this.budget = budget;
        this.categories = categories;
    }

    /**
     * Get the name of the budget
     *
     * @return String The budget name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the expense amount of the budget
     *
     * @return long The budget expense amount
     */
    public long getExpense() {
        return expense;
    }

    /**
     * Get the income amount of the budget
     *
     * @return long The budget income amount
     */
    public long getIncome() {
        return income;
    }

    /**
     * Get the amount of the budget
     *
     * @return long The budget amount
     */
    public long getBudget() {
        return budget;
    }

    /**
     * Get the categories list of the budget
     *
     * @return List<BudgetCategory> The budget categories list
     */
    public List<BudgetCategory> getCategories() {
        return categories;
    }
}
