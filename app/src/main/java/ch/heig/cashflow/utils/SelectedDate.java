/**
 * Model SelectedDate
 * <p>
 * Used to store current date application context
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.fragments.MonthFragment
 */

package ch.heig.cashflow.utils;

import java.util.Calendar;
import java.util.Observable;

import ch.heig.cashflow.network.utils.Date;

public class SelectedDate extends Observable {

    private int year;
    private int month;
    private int day;
    private static SelectedDate instance;

    private SelectedDate() {
    }

    /**
     * Singleton
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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /**
     * Set date and notify obeservers
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        setChanged();
        notifyObservers();
    }

    /**
     * @return string format
     */
    public String toString() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return Date.sdf.format(c.getTime());
    }
}
