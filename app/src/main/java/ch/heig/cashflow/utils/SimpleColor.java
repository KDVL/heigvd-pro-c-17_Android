package ch.heig.cashflow.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

public class SimpleColor {
    private Context context;

    public SimpleColor(Context context) {
        this.context = context;
    }

    public int get(int color) {
        return ContextCompat.getColor(context, color);
    }

    public ColorStateList getState(int color) {
        return ColorStateList.valueOf(ContextCompat.getColor(context, color));
    }
}
