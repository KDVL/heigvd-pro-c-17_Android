/**
 * Model BudgetCategory
 *
 * <p>
 * Defines a budget category an expense amount, an income amount, a budget amount
 * and a list of categories.
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.models;

public class BudgetCategory {

    private final long expense;
    private final long income;
    private final long budget;
    private final Category category;

    /**
     * The Budget constructor
     *
     * @param expense  The budget expense amount
     * @param income   The budget income amount
     * @param budget   The budget amount
     * @param category The budget category
     */
    public BudgetCategory(long expense, long income, long budget, Category category) {
        this.expense = expense;
        this.income = income;
        this.budget = budget;
        this.category = category;
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
     * Get the category of the budget
     *
     * @return Category The budget category
     */
    public Category getCategory() {
        return category;
    }
}
