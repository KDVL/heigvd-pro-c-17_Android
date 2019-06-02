/**
 * The class that provides us with the elements of the application
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.utils.ApplicationResources
 */

package ch.heig.cashflow.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ApplicationResources {
    private Context context;

    /**
     * The ApplicationResources constructor
     *
     * @param context The context of the application
     */
    public ApplicationResources(Context context) {
        this.context = context;
    }

    /**
     * Look for the id of the element in the folder drawable
     *
     * @param resName The name of item
     * @return int The id of item, 0 if not found
     */
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName, "drawable", pkgName);
    }

    /**
     * Allows to request a string to the context
     *
     * @param str The id of string
     * @return String The requested string
     */
    public String getString(int str) {
        return context.getString(str);
    }

    /**
     * Allows to request a color to the context
     *
     * @param color The id of color
     * @return int The requested color
     */
    public int getColor(int color) {
        return ContextCompat.getColor(context, color);
    }
}
