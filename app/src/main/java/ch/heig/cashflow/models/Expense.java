/**
 * Model Expense
 * <i>The act or an instance of expending.</i>
 *
 * <p>
 * Defines a {@code Transaction} of {@code Type} Expense.
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.models;

import java.io.Serializable;

import ch.heig.cashflow.utils.Type;

@SuppressWarnings("serial")
public class Expense extends Transaction implements Serializable {

    private final static Type TYPE = Type.EXPENSE;

    /**
     * The Expense constructor
     *
     * @param id          The expense id
     * @param date        The expense date
     * @param category    The expense category
     * @param amount      The expense amount
     * @param description The expense description
     */
    public Expense(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
