/**
 * Model SelectedDate
 *
 * Used to store current date application context
 * @see ch.heig.cashflow.fragments.MonthFragment
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.models;

import java.util.Calendar;
import java.util.Observable;

public class SelectedDate extends Observable {

    private int year;
    private int month;
    private int day;
    private static SelectedDate instance;

    private SelectedDate(){}

    /**
     * Singleton
     */
    public synchronized static SelectedDate getInstance(){

        if(instance == null){
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

    public void setDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;

        setChanged();
        notifyObservers();
    }


}
