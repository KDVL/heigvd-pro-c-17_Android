/**
 * Add or edit an activity
 *
 * <p>
 * Works with The incomes and The expenses.
 *
 * @author Kevin DO VALE
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
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter;
import ch.heig.cashflow.fragments.DatePickerFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.TransactionService;
import ch.heig.cashflow.network.utils.Date;

public class AddOrEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TransactionService.Callback, CategoriesService.Callback {

    private static final String TAG = AddOrEditActivity.class.getSimpleName();

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

    private List<Category> categories;
    private TransactionAddOrEditAdapter adapter = null;

    /**
     * onCreate
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);

        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i != null) {
            adapter = (TransactionAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
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
     * Set the date
     *
     * @param datePicker The picker
     * @param year       The year
     * @param month      The month
     * @param dayOfMonth The day
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
     * Save datas
     *
     * @param view The view
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

        if (note == null || note == "") {
            Toast.makeText(getApplicationContext(), "Description non conforme", Toast.LENGTH_LONG).show();
            return;

        }

        Category c = categories.get(categoriesSpinner.getSelectedItemPosition());

        addButton.setEnabled(false);

        // Set informations
        adapter.getTransaction().setAmount(amount);
        adapter.getTransaction().setCategory(c);
        adapter.getTransaction().setDescription(note);

        // Do edit or add
        adapter.performAction();
    }

    /**
     * Get categories from service
     *
     * @param cat The list
     */
    @Override
    public void getFinished(List<Category> cat) {

        categories = new LinkedList<>();

        //add only enable
        for (Category c : cat) {
            if (c.isEnabled())
                categories.add(c);
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for (Category c : categories) {
            arrayList.add(c.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(adapter);

        //adapter know who will be selected
        this.adapter.selectCategory(this.categories, categoriesSpinner);
    }

    /**
     * Service finished
     *
     * @param isFinished True if insert or add works
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
     * Service finished with error
     *
     * @param error The error
     */
    @Override
    public void connectionFailed(String error) {
        addButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Impossible d'effectuer cette opération, vérifiez vos saisies", Toast.LENGTH_LONG).show();
    }

    /**
     * Used by service
     *
     * @return Context The context of the application
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * Needed by service but not used
     *
     * @param transaction The transaction
     */
    @Override
    public void getFinished(Transaction transaction) {
    }
}
