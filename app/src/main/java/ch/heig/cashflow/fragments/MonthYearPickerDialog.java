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

/**
 * The class for the custom dialog management of the month and year
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */
public class MonthYearPickerDialog extends DialogFragment {

    private static final String TAG = MonthYearPickerDialog.class.getSimpleName();

    private DatePickerDialog.OnDateSetListener listener = null;

    /**
     * Add the event listener
     *
     * @param listener event listener
     */
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    /**
     * Override to build your own custom Dialog container.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state. This value may be null.
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

