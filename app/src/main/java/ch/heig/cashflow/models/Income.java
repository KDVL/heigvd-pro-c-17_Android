/**
 * Model Income
 * <i>A gain or recurrent benefit usually measured in money that derives from capital or labor.</i>
 *
 * <p>
 * Defines a {@code Transaction} of {@code Type} Income.
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.models;

import java.io.Serializable;

import ch.heig.cashflow.utils.Type;

@SuppressWarnings("serial")
public class Income extends Transaction implements Serializable {

    private final static Type TYPE = Type.INCOME;

    /**
     * The Expense constructor
     *
     * @param id          The expense id
     * @param date        The expense date
     * @param category    The expense category
     * @param amount      The expense amount
     * @param description The expense description
     */
    public Income(long id, String date, Category category, long amount, String description) {
        super(id, date, category, amount, TYPE, description);
    }
}
