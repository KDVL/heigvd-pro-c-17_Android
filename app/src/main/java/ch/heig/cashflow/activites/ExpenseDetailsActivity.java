package ch.heig.cashflow.activites;

import android.content.Context;
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
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public class ExpenseDetailsActivity extends AppCompatActivity implements TransactionService.Callback {
    private static final String TAG = "ExpenseDetailsActivity";

    private TransactionService ts;

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

        ts = new TransactionService(this);

        setTitle(editExpenseAdapter.getViewTitle(getApplicationContext()));


        expenseIcon = findViewById(R.id.expenseToEditIcon);
        expenseDate = findViewById(R.id.expenseToEditDate);
        expenseAmount = findViewById(R.id.expenseToEditAmount);
        expenseDesc = findViewById(R.id.expenseToEditDesc);

        int iconImageId = this.getDrawableResIdByName(editExpenseAdapter.getTransaction().getCategory().getIconName());
        expenseIcon.setImageResource(iconImageId);
        expenseIcon.getDrawable().setTint(Color.parseColor("#FFFFFF"));

        expenseDate.setText(editExpenseAdapter.getTransaction().getDate());
        expenseAmount.setText(String.valueOf(editExpenseAdapter.getTransaction().getAmountFloat()));
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
                edit();
                return true;

            case R.id.expense_delete:
                delete();
                return true;

            default:
                return super.onOptionsItemSelected(_item);

        }
    }

    private void edit() {
        Intent categorieChoice = new Intent(this, AddOrEditActivity.class);
        categorieChoice.putExtra(getResources().getString(R.string.transaction_adapter_key), editExpenseAdapter);
        startActivity(categorieChoice);
    }

    private void delete() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer la transaction?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ts.delete(editExpenseAdapter.getTransaction());
                        finish();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        dialog.show();
    }

    private void retour() {
        Intent main = new Intent(this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    //TODO: Gerer Calback
    @Override
    public void connectionFailed(String error) {

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

