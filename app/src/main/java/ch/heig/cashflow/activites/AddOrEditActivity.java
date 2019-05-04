package ch.heig.cashflow.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.AddOrEditAdapter;
import ch.heig.cashflow.fragments.DatePickerFragment;


public class AddOrEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddOrEditActivity";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String curentDateString = null;

    private AddOrEditAdapter adapter = null;

    @BindView(R.id.input_categorie)
    Spinner categoriesSpinner;
    @BindView(R.id.datePicker)
    Button selectDate;
    @BindView(R.id.input_description)
    EditText descriptionText;
    @BindView(R.id.input_price)
    EditText priceText;
    @BindView(R.id.btn_add)
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);

        ButterKnife.bind(this);

        setTitle("Gestion de transaction");

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(getResources().getString(R.string.transaction_adapter_key)))
                adapter = (AddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
            /*
            else if (i.hasExtra("editIncomeAdapter"))
                adapter = (EditIncomeAdapter) i.getSerializableExtra("editIncomeAdapter");
            else if (i.hasExtra("addExpenseAdapter"))
                adapter = (EditIncomeAdapter) i.getSerializableExtra("addExpenseAdapter");
            else if (i.hasExtra("addIncomeAdapter"))
                adapter = (EditIncomeAdapter) i.getSerializableExtra("addIncomeAdapter");
            */
        }

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        if (adapter.getTransaction() != null) {
            descriptionText.setText(adapter.getTransaction().getDescription());
            priceText.setText(String.valueOf(adapter.getTransaction().getAmount()));
        }

        Calendar c = Calendar.getInstance();
        curentDateString = sdf.format(c.getTime());
        selectDate.setText(curentDateString);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        curentDateString = sdf.format(c.getTime());
        selectDate.setText(curentDateString);
    }
}
