/**
 * TODO Aleksandar
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SimpleColor;

public class TransactionDetailsActivity extends AppCompatActivity implements TransactionService.Callback {

    private static final String TAG = TransactionDetailsActivity.class.getSimpleName();

    private TransactionService ts;
    private TransactionAddOrEditAdapter adapter = null;
    private ImageView expenseIcon = null;
    private TextView expenseDate = null;
    private TextView expenseAmount = null;
    private TextView expenseDesc = null;

    /**
     * On activity create
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleColor sp = new SimpleColor(getContext());

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

        int iconImageId = this.getDrawableResIdByName(adapter.getTransaction().getCategory().getIconName());
        if (iconImageId != 0) {
            expenseIcon.setImageResource(iconImageId);
            expenseIcon.getDrawable().setTint(sp.get(R.color.white));
        }

        expenseDate.setText(adapter.getTransaction().getDate());
        expenseAmount.setText(Currency.format(adapter.getTransaction().getAmount()));
        expenseDesc.setText(adapter.getTransaction().getDescription());
    }

    /**
     * TODO
     *
     * @param resName
     * @return int
     */
    public int getDrawableResIdByName(String resName) {
        String pkgName = getApplicationContext().getPackageName();
        // Return 0 if not found.
        return getApplicationContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    /**
     * TODO
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * TODO
     *
     * @param item
     * @return boolean
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
     * TODO
     */
    private void edit() {
        Intent categorieChoice = new Intent(this, AddOrEditActivity.class);
        categorieChoice.putExtra(getResources().getString(R.string.transaction_adapter_key), adapter);
        startActivity(categorieChoice);
        finish();
    }

    /**
     * TODO
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
