/**
 * TODO Aleksandar
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

import ch.heig.cashflow.R;

public class MonthYearPickerDialog extends DialogFragment {

    private static final String TAG = MonthYearPickerDialog.class.getSimpleName();

    private DatePickerDialog.OnDateSetListener listener = null;

    /**
     * TODO Aleksandar
     *
     * @param listener
     */
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    /**
     * TODO Aleksandar
     *
     * @param savedInstanceState The saved instance state
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar c = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_dialog, null);
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        int month = c.get(Calendar.MONTH) + 1;
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);

        int year = c.get(Calendar.YEAR);
        yearPicker.setMinValue(year - 10);
        yearPicker.setMaxValue(year + 10);
        yearPicker.setValue(year);

        builder.setView(dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue() - 1, 0);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthYearPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}

