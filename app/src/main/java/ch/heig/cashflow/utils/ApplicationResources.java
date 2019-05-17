/**
 * The class that provides us with the elements of the application
 *
 * @authors Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.utils.ApplicationResources
 */

package ch.heig.cashflow.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ApplicationResources {
    private Context context;

    /**
     * Constructor
     *
     * @param context context of application
     */
    public ApplicationResources(Context context) {
        this.context = context;
    }

    /**
     * Look for the id of the element in the folder drawable
     *
     * @param resName Name of item
     * @return The id of item
     * @note Return 0 if not found
     */
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName, "drawable", pkgName);
    }

    /**
     * Allows to request a string to the context
     *
     * @param str id of string
     * @return the requested string
     */
    public String getString(int str) {
        return context.getString(str);
    }

    /**
     * Allows to request a color to the context
     *
     * @param color id of color
     * @return the requested color
     */
    public int getColor(int color) {
        return ContextCompat.getColor(context, color);
    }
}
