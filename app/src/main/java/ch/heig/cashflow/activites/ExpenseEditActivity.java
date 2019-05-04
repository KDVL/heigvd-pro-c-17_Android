package ch.heig.cashflow.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.ExpenseService;
import ch.heig.cashflow.models.Expense;

public class ExpenseEditActivity extends AppCompatActivity {
    private static final String TAG = "ExpenseEditActivity";
    private long expenseId = 0;

    private ExpenseService expenseService = null;

    private ImageView expenseIcon = null;
    private TextView expenseDate = null;
    private TextView expenseAmount = null;
    private TextView expenseDesc = null;

    private Expense expenseToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_edit);

        setTitle("Dépense détails");

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra("expenseId")) {
                expenseId = i.getLongExtra("expenseId", 0);
            }
        }

        expenseIcon = findViewById(R.id.expenseToEditIcon);
        expenseDate = findViewById(R.id.expenseToEditDate);
        expenseAmount = findViewById(R.id.expenseToEditAmount);
        expenseDesc = findViewById(R.id.expenseToEditDesc);

        expenseService = new ExpenseService();
        expenseToEdit = expenseService.getExpenseById(expenseId);

        int iconImageId = this.getDrawableResIdByName(expenseToEdit.getCategory().getIconName());
        expenseIcon.setImageResource(iconImageId);
        expenseIcon.getDrawable().setTint(Color.parseColor("#222222"));

        expenseDate.setText(expenseToEdit.getDate());
        expenseAmount.setText(String.valueOf(expenseToEdit.getAmount()));
        expenseDesc.setText(expenseToEdit.getDescription());
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
        Intent categorieChoice = new Intent(this, CategoryChoiceActivity.class);
        categorieChoice.putExtra("expenseId", expenseId);
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
                            expenseService.deleteExpense(expenseId);
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

