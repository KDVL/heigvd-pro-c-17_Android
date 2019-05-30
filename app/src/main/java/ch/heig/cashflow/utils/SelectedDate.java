/**
 * Model SelectedDate used to store current date application context
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.fragments.MonthFragment
 */

package ch.heig.cashflow.utils;

import java.util.Calendar;
import java.util.Observable;

import ch.heig.cashflow.network.utils.Date;

public class SelectedDate extends Observable {

    private static SelectedDate instance;
    private int year;
    private int month;
    private int day;

    /**
     * The private empty SelectedDate constructor
     *
     * <p>
     * Needed to use the Singleton pattern
     */
    private SelectedDate() {
    }

    /**
     * Singleton
     *
     * @return SelectedDate The unique SelectedDate instance
     */
    public synchronized static SelectedDate getInstance() {

        if (instance == null) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            instance = new SelectedDate();
            instance.setDate(year, month, day);
        }

        return instance;
    }

    /**
     * @return int The year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return int The month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return int The day
     */
    public int getDay() {
        return day;
    }

    /**
     * Set date and notify observers
     *
     * @param year  The year
     * @param month The month
     * @param day   The day
     */
    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        setChanged();
        notifyObservers();
    }

    /**
     * @return String format
     */
    public String toString() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return Date.sdf.format(c.getTime());
    }
}
