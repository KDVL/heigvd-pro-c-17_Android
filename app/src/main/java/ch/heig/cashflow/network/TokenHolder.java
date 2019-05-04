package ch.heig.cashflow.network;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenHolder {

    private static final String MY_PREFS_NAME = "LOGIN_TOKEN";

    public static void saveToken(Context context, String token){

        SharedPreferences.Editor editor =
                context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
                        .edit();

        editor.clear();
        editor.putString(MY_PREFS_NAME, token);
        editor.apply();
    }

    public static String getToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(MY_PREFS_NAME, "");
    }
}
