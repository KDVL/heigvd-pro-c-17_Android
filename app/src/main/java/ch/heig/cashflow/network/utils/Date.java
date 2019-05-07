package ch.heig.cashflow.network.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static String getCurrentDateServeurFormat(){
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }
}
