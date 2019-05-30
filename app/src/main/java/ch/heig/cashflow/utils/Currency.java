/**
 * Format Currency in Switzerland style
 *
 * <p>
 * The transactions come from database as simple decimal
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.utils;

public class Currency {

    /**
     * Format a decimal currency number as Switzerland standard
     *
     * <p>
     * TODO : Afficher toujours 2 zero
     * TODO : Separateur de milliers
     *
     * @param decimal The decimal to format
     * @return String The decimal formatted as standard
     */
    public static final String format(long decimal) {
        return String.format("%s.%s CHF", decimal / 100, Math.abs(decimal % 100));
    }

    /**
     * Get a decimal as long
     *
     * @param value The decimal
     * @return long The decimal as long
     */
    public long getLong(String value) {
        float f = Float.valueOf(value);
        return (long) (f * 100);
    }

    /**
     * Get a decimal as float
     *
     * @param value The decimal
     * @return float The decimal as float
     */
    public float getFloat(long value) {
        return (float) value / 100;
    }
}
