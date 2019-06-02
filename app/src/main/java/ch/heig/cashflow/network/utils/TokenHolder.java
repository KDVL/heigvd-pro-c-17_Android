/**
 * Token storage handler
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenHolder {

    private static final String MY_PREFS_NAME = "LOGIN_TOKEN";

    /**
     * Save the user token
     *
     * @param context The application context
     * @param token   The token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.putString(MY_PREFS_NAME, token);
        editor.apply();
    }

    /**
     * Return the token
     *
     * @param context The application context
     * @return String The token
     */
    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(MY_PREFS_NAME, "");
    }


    /**
     * Check if user is logged
     *
     * @param context The application context
     * @return boolean True if is logged, false otherwise
     */
    public static boolean isLogged(Context context) {
        return getToken(context) != null && getToken(context) != "";
    }
}
