/**
 * The main activity of project
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.transactions.TransactionAddAdapter;
import ch.heig.cashflow.adapters.transactions.TransactionAddExpenseAdapter;
import ch.heig.cashflow.adapters.transactions.TransactionAddIncomeAdapter;
import ch.heig.cashflow.fragments.CategoriesFragmentTabHost;
import ch.heig.cashflow.fragments.ContainerChartsFragment;
import ch.heig.cashflow.fragments.DashboardFragment;
import ch.heig.cashflow.fragments.MonthFragment;
import ch.heig.cashflow.fragments.TransactionFragment;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.AuthValidationService;
import ch.heig.cashflow.network.utils.TokenHolder;


public class MainActivity extends AppCompatActivity implements AuthValidationService.Callback {

    private TransactionAddAdapter transactionAddAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    setTitle(R.string.title_dashboard);
                    fragment = DashboardFragment.newInstance();
                    break;
                case R.id.navigation_expense:
                    setTitle(R.string.title_expenses);
                    transactionAddAdapter = new TransactionAddExpenseAdapter();
                    fragment = TransactionFragment.newInstance();
                    ((TransactionFragment) fragment).setType(Type.EXPENSE);
                    break;
                case R.id.navigation_earning:
                    setTitle(R.string.title_earning);
                    transactionAddAdapter = new TransactionAddIncomeAdapter();
                    fragment = TransactionFragment.newInstance();
                    ((TransactionFragment) fragment).setType(Type.INCOME);
                    break;
                case R.id.navigation_charts:
                    setTitle(R.string.title_charts);
                    fragment = ContainerChartsFragment.newInstance();
                    break;
                case R.id.navigation_categories:
                    setTitle(R.string.title_categories);
                    fragment = CategoriesFragmentTabHost.newInstance();
                    break;
            }
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
            return true;
        }
    };

    /**
     * On activity create
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, DashboardFragment.newInstance());
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Verify token
        new AuthValidationService(this);

        if (!TokenHolder.isLogged(getApplicationContext())) {
            showLogin();
        }

        setTitle(R.string.title_dashboard);
    }

    /**
     * onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Show the login activity
     */
    private void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * onCreateOptionsMenu
     *
     * @param menu the menu
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item menuitem selected
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_add:
                Intent addOrEdit = new Intent(this, AddOrEditActivity.class);
                addOrEdit.putExtra(getResources().getString(R.string.transaction_adapter_key), transactionAddAdapter);
                startActivity(addOrEdit);
                return true;
            case R.id.actionbar_calendar:
                DialogFragment datePicker = new MonthFragment();
                datePicker.show(getSupportFragmentManager(), "picker");
                return true;
            case R.id.actionbar_disconnect:
                TokenHolder.saveToken(getApplicationContext(), "");
                showLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param isLogged The user logged flag
     */
    @Override
    public void authVerification(boolean isLogged) {
        if (!isLogged && TokenHolder.isLogged(getApplicationContext()))
            showLogin();
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
     * @param hasCapture The pointer capture flag
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
