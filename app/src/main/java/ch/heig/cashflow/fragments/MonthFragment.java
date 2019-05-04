/**
 * Select month/year day and store in SelectedDate
 *
 * @see ch.heig.cashflow.models.SelectedDate
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.SelectedDate;

public class MonthFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        SelectedDate d = SelectedDate.getInstance();

        return new DatePickerDialog(getActivity(), R.style.AppTheme_Dark_DarkDialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        SelectedDate.getInstance().setDate(y,m,d);
                    }
                },
                d.getYear(), d.getMonth(), d.getDay());
    }
}
