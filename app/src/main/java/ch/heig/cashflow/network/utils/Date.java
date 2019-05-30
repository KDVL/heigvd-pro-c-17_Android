/**
 * Date utils
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Get the current date as server format standard
     *
     * @return string The current date
     */
    public static String getCurrentDateServerFormat() {
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }
}
