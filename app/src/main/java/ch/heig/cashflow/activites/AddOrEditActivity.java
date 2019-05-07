package ch.heig.cashflow.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.AddOrEditAdapter;
import ch.heig.cashflow.fragments.DatePickerFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.CustomOnItemSelectedListener;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.TransactionService;


public class AddOrEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TransactionService.Callback, CategoriesService.Callback {
    private static final String TAG = "AddOrEditActivity";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SelectedDate selectedDate = SelectedDate.getInstance();

    String curentDateString = null;

    private TransactionService ts;
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
        categoriesSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        ts = new TransactionService(this);

        Intent i = getIntent();
        if (i != null) {
            adapter = (AddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
            adapter.setCallback(this);
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

    public void saveExpense(View view) {
        String amount = priceText.getText().toString();
        Log.i(TAG, "Montant saisi: " + amount);

        if (!amount.equals("")) {
            if (amount.length() < 7) {

                String selectedCategory = String.valueOf(categoriesSpinner.getSelectedItem());
                Log.i(TAG, "Catégorie choisie: " + selectedCategory);

                // TODO: Passer montant en long centimes et sécuriser
                long amountCentimes = Integer.valueOf(amount) * 100;
                Log.i(TAG, "Montant saisi transformé en centimes: " + amountCentimes);

                String note = descriptionText.getText().toString();
                Log.i(TAG, "La description de la dépense: " + note);

                //TODO: Comment obtenir la catégorie
                Category c = new Category(1, "Boisson", "cad_drink", Type.EXPENSE, 200, true);
                Transaction t = new Expense(1, curentDateString, c, amountCentimes, note);

                // TODO: Quel ID nouvel dépense?
                if (adapter.getTransaction() != null) {
                    ts.update(new Expense(adapter.getTransaction().getID(), curentDateString, c, amountCentimes, note));
                } else {
                    ts.add(t);
                }

            } else {
                Toast.makeText(getApplicationContext(), "Max 7 caractères depassé", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Montant pas saisi!", Toast.LENGTH_LONG).show();
        }

        //finish();
        back();
    }

    private void back() {
        Intent main = new Intent(this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = getApplicationContext().getPackageName();
        // Return 0 if not found.
        return getApplicationContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public void getFinished(List<Category> categories) {

        ArrayList<String> arrayList = new ArrayList<>();
        for (Category cat : categories){
            arrayList.add(cat.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(adapter);

        this.adapter.selectCategorie(categories, categoriesSpinner);
    }

    @Override
    public void getFinished(Transaction transaction) {

    }

    @Override
    public void operationFinished(boolean isFinished) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
