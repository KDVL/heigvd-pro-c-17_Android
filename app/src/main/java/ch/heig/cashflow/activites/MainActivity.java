/**
 * The main activity of project
 *
 * @authors Kevin DO VALE
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

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.AddAdapter;
import ch.heig.cashflow.adapters.AddExpenseAdapter;
import ch.heig.cashflow.adapters.AddIncomeAdapter;
import ch.heig.cashflow.adapters.EditAdapter;
import ch.heig.cashflow.adapters.EditExpenseAdapter;
import ch.heig.cashflow.fragments.CategoryFragment;
import ch.heig.cashflow.fragments.TransactionFragment;
import ch.heig.cashflow.fragments.MonthFragment;
import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.AuthValidationService;
import ch.heig.cashflow.network.utils.TokenHolder;


public class MainActivity extends AppCompatActivity implements AuthValidationService.Callback {
    private static final String TAG = "MainActivity";
    private AddAdapter addAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    setTitle(R.string.title_dashboard);
                    //fragment = DashboardFragment.newInstance(); // TODO: KDVL work
                    break;
                case R.id.navigation_expense:
                    setTitle(R.string.title_expenses);
                    addAdapter = new AddExpenseAdapter();
                    fragment = TransactionFragment.newInstance();
                    ((TransactionFragment) fragment).setType(Type.EXPENSE);
                    break;
                case R.id.navigation_earning:
                    setTitle(R.string.title_earning);
                    addAdapter = new AddIncomeAdapter();
                    fragment = TransactionFragment.newInstance();
                    ((TransactionFragment) fragment).setType(Type.INCOME);
                    break;
                case R.id.navigation_charts:
                    setTitle(R.string.title_charts);
                    fragment = ServicesFragment.newInstance();
                    //fragment = ChartsFragment.newInstance();
                    break;
                case R.id.navigation_categories:
                    setTitle(R.string.title_categories);
                    fragment = CategoryFragment.newInstance();
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
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAdapter = new AddExpenseAdapter(); //TODO: delete test

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, TransactionFragment.newInstance()); //TODO: begin DashboardFragment
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
     * show login Activity
     */
    private void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * onCreateOptionsMenu
     *
     * @param menu the menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item menuitem selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_add:
                Intent addOrEdit = new Intent(this, AddOrEditActivity.class);
                addOrEdit.putExtra(getResources().getString(R.string.transaction_adapter_key), addAdapter);
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

    @Override
    public void authVerification(boolean isLogged) {
        if (!isLogged && TokenHolder.isLogged(getApplicationContext()))
            showLogin();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
