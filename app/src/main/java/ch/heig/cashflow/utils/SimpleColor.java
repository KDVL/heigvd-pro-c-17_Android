/**
 * Allows to get a simple color without adding all the boring Android stuff
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

public class SimpleColor {

    private Context context;

    /**
     * The SimpleColor constructor
     *
     * @param context The context
     */
    public SimpleColor(Context context) {
        this.context = context;
    }

    /**
     * Get a color (int) as Android format
     *
     * @param color The color
     * @return int The color as Android format
     */
    public int get(int color) {
        return ContextCompat.getColor(context, color);
    }

    /**
     * Get a color (int) as Android format (ColorStateList)
     *
     * @param color The color
     * @return ColorStateList The color as Android format
     */
    public ColorStateList getState(int color) {
        return ColorStateList.valueOf(ContextCompat.getColor(context, color));
    }
}
