/**
 * TODO Aleksandar
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import ch.heig.cashflow.R;

public class DatePickerFragment extends DialogFragment {
    /**
     * TODO Aleksandar
     *
     * @param savedInstanceState The saved instance state
     * @return Dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int resId = R.style.AppTheme_Dark_DarkDialog;
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), resId, listener, year, month, day);
        return dialog;
    }
}
