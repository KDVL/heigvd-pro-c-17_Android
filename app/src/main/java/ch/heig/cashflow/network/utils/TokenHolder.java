/**
 * Token storage handler
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.network.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenHolder {

    private static final String MY_PREFS_NAME = "LOGIN_TOKEN";

    /**
     * Save user token
     * @param context application context
     * @param token the token
     */
    public static void saveToken(Context context, String token){

        SharedPreferences.Editor editor =
                context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
                        .edit();

        editor.clear();
        editor.putString(MY_PREFS_NAME, token);
        editor.apply();
    }

    /**
     * return token
     * @param context application context
     * @return the token
     */
    public static String getToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(MY_PREFS_NAME, "");
    }


    /**
     * check if user is logged
     * @param context application context
     * @return true if is logged
     */
    public static boolean isLogged(Context context){
       return getToken(context) != null && getToken(context) != "";
    }
}
