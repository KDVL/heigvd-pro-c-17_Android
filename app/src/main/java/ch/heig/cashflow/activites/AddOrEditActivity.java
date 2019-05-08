/**
 * Add or edit activity
 * works with income and expenses
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.AddOrEditAdapter;
import ch.heig.cashflow.fragments.DatePickerFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.TransactionService;
import ch.heig.cashflow.network.utils.Date;


public class AddOrEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TransactionService.Callback, CategoriesService.Callback {
    private List<Category> enabledCategories;

    private AddOrEditAdapter adapter = null;

    @BindView(R.id.input_categorie)
    Spinner categoriesSpinner;
    @BindView(R.id.date_picker)
    Button selectDate;
    @BindView(R.id.input_description)
    EditText descriptionText;
    @BindView(R.id.input_price)
    EditText priceText;
    @BindView(R.id.btn_add)
    Button addButton;


    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);

        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i != null) {
            adapter = (AddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
            adapter.setCallbackTransaction(this);
            adapter.setCallbackCategorie(this);
        }

        setTitle(adapter.getViewTitle(getApplicationContext()));

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        if (adapter.getTransaction() != null) {
            descriptionText.setText(adapter.getTransaction().getDescription());
            priceText.setText(String.valueOf(adapter.getAmount()));
        }

        selectDate.setText(adapter.getTransaction().getDate());
    }

    /**
     * set date
     *
     * @param datePicker the picker
     * @param year       the year
     * @param month      the month
     * @param dayOfMonth the day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String d = Date.sdf.format(c.getTime());
        adapter.getTransaction().setDate(d);

        selectDate.setText(d);
    }

    /**
     * save data
     *
     * @param view the view
     */
    public void save(View view) {

        String amountText = priceText.getText().toString();

        if (amountText.equals("")) {
            Toast.makeText(getApplicationContext(), "Montant pas saisi!", Toast.LENGTH_LONG).show();
            return;
        }
        if (amountText.length() > 7) {
            Toast.makeText(getApplicationContext(), "Max 7 caractères depassé", Toast.LENGTH_LONG).show();
            return;
        }

        float amount = Float.valueOf(amountText);

        if (amount <= 0) {
            Toast.makeText(getApplicationContext(), "Montant non conforme", Toast.LENGTH_LONG).show();
            return;
        }

        String note = descriptionText.getText().toString();

        if(note == null || note == ""){
            Toast.makeText(getApplicationContext(), "Description non conforme", Toast.LENGTH_LONG).show();
            return;

        }

        Category c = enabledCategories.get(categoriesSpinner.getSelectedItemPosition());

        addButton.setEnabled(false);

        //set informations
        adapter.getTransaction().setAmount(amount);
        adapter.getTransaction().setCategory(c);
        adapter.getTransaction().setDescription(note);

        // do edit or add
        adapter.performAction();
    }



    /**
     * get enabledCategories from service
     *
     * @param categories the list
     */
    @Override
    public void getFinished(List<Category> categories) {

        enabledCategories = categories;

        //remove disable enabledCategories
        for (Category c : enabledCategories) {
            if (!c.isEnabled())
                enabledCategories.remove(c);
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for (Category cat : enabledCategories) {
            arrayList.add(cat.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(adapter);

        //adapter know who will be selected
        this.adapter.selectCategorie(enabledCategories, categoriesSpinner);
    }

    /**
     * service finished
     *
     * @param isFinished true if insert or add works
     */
    @Override
    public void operationFinished(boolean isFinished) {
        if (isFinished) {
            finish();
        } else {
            connectionFailed("");
        }
    }

    /**
     * service finished with error
     *
     * @param error the error
     */
    @Override
    public void connectionFailed(String error) {
        addButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Impossible d'effectuer cette opération, Vérifiez vos saisies", Toast.LENGTH_LONG).show();
    }


    /**
     * used by service
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * Not used here
     *
     * @param transaction
     */
    @Override
    public void getFinished(Transaction transaction) {}
}
