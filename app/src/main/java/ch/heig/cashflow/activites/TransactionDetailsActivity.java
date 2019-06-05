/**
 * Activity to see transaction details
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.Currency;

public class TransactionDetailsActivity extends AppCompatActivity implements TransactionService.Callback {
    private TransactionService ts;
    private TransactionAddOrEditAdapter adapter = null;
    private ImageView expenseIcon = null;
    private TextView expenseDate = null;
    private TextView expenseAmount = null;
    private TextView expenseDesc = null;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Note: Otherwise it is null. This value may be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationResources appRes = new ApplicationResources(getContext());

        setContentView(R.layout.activity_transaction_details);

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(getResources().getString(R.string.transaction_adapter_key))) {
                adapter = (TransactionAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.transaction_adapter_key));
            }
        }

        ts = new TransactionService(this);

        setTitle(adapter.getViewTitle(getApplicationContext()));

        expenseIcon = findViewById(R.id.edit_icon);
        expenseDate = findViewById(R.id.edit_date);
        expenseAmount = findViewById(R.id.edit_amount);
        expenseDesc = findViewById(R.id.edit_description);

        int iconImageId = appRes.getDrawableResIdByName(adapter.getTransaction().getCategory().getIconName());
        if (iconImageId != 0) {
            expenseIcon.setImageResource(iconImageId);

            int white = appRes.getColor(R.color.white);

            if(white != 0)
                expenseIcon.getDrawable().setTint(white);
        }

        expenseDate.setText(adapter.getTransaction().getDate());
        expenseAmount.setText(Currency.format(adapter.getTransaction().getAmount()));
        expenseDesc.setText(adapter.getTransaction().getDescription());
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expense_edit:
                edit();
                return true;

            case R.id.expense_delete:
                delete();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Starts the activity for modifying a transaction
     */
    private void edit() {
        Intent categorieChoice = new Intent(this, AddOrEditActivity.class);
        categorieChoice.putExtra(getResources().getString(R.string.transaction_adapter_key), adapter);
        startActivity(categorieChoice);
        finish();
    }

    /**
     * The dialog for confirmation of deletion of a transaction
     */
    private void delete() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer la category?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ts.delete(adapter.getTransaction());
                        finish();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        dialog.show();
    }

    /**
     * Return off call API if failed
     *
     * @param error The error message
     */
    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
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

    /**
     * Needed by service but not used
     *
     * @param isFinished The operation finish flag
     */
    @Override
    public void operationFinished(boolean isFinished) {

    }
}
