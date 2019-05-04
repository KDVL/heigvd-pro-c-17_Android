package ch.heig.cashflow.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.AddOrEditAdapter;
import ch.heig.cashflow.adapters.EditExpenseAdapter;
import ch.heig.cashflow.adapters.ExpenseService;
import ch.heig.cashflow.models.Expense;

public class ExpenseDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ExpenseDetailsActivity";

    private static final String[] TITLE = {"Dépense détails", "Revenu détails"};

    private ExpenseService expenseService = null;

    private AddOrEditAdapter editExpenseAdapter = null;

    private ImageView expenseIcon = null;
    private TextView expenseDate = null;
    private TextView expenseAmount = null;
    private TextView expenseDesc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(getResources().getString(R.string.transaction_adapter_key))) {
                editExpenseAdapter = (AddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
            }
        }

        setTitle(TITLE[editExpenseAdapter.getTransaction().getType().ordinal()]);

        expenseIcon = findViewById(R.id.expenseToEditIcon);
        expenseDate = findViewById(R.id.expenseToEditDate);
        expenseAmount = findViewById(R.id.expenseToEditAmount);
        expenseDesc = findViewById(R.id.expenseToEditDesc);

        expenseService = new ExpenseService();

        int iconImageId = this.getDrawableResIdByName(editExpenseAdapter.getTransaction().getCategory().getIconName());
        expenseIcon.setImageResource(iconImageId);
        expenseIcon.getDrawable().setTint(Color.parseColor("#FFFFFF"));

        expenseDate.setText(editExpenseAdapter.getTransaction().getDate());
        expenseAmount.setText(String.valueOf(editExpenseAdapter.getTransaction().getAmount()));
        expenseDesc.setText(editExpenseAdapter.getTransaction().getDescription());
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = getApplicationContext().getPackageName();
        // Return 0 if not found.
        return getApplicationContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_expense_edit, _menu);
        return super.onCreateOptionsMenu(_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case R.id.expense_edit:
                modifierDepense();
                return true;

            case R.id.expense_delete:
                supprimerDepense();
                return true;

            default:
                return super.onOptionsItemSelected(_item);

        }
    }

    private void modifierDepense() {
        Intent categorieChoice = new Intent(this, AddOrEditActivity.class);
        categorieChoice.putExtra(getResources().getString(R.string.transaction_adapter_key), editExpenseAdapter);
        startActivity(categorieChoice);
    }

    private void supprimerDepense() {
        final EditText password = new EditText(this);

        // Set the default text to a link of the Queen
        password.setHint("ok");

        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Ecrivez ok pour supprimer")
                .setView(password)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String saisi = password.getText().toString();
                        if (saisi.equals("ok")) {
                            expenseService.deleteExpense(editExpenseAdapter.getTransaction().getID());
                            retour();
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    private void retour() {
        Intent main = new Intent(this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}

