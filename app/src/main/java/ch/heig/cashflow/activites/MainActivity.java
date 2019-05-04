/**
 * The main activity of project
 *
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
import ch.heig.cashflow.fragments.ChartsFragment;
import ch.heig.cashflow.fragments.ExpenseFragment;
import ch.heig.cashflow.network.services.AuthValidationService;
import ch.heig.cashflow.network.utils.TokenHolder;


public class MainActivity extends AppCompatActivity  implements AuthValidationService.Callback {
    private static final String TAG = "MainActivity";
    private AddAdapter addAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_expense:
                    fragment = ExpenseFragment.newInstance();
                    addAdapter = new AddExpenseAdapter();
                    break;
                case R.id.navigation_earning:
                    fragment = ExpenseFragment.newInstance();
                    addAdapter = new AddIncomeAdapter();
                    break;
                case R.id.navigation_charts:
                    fragment = ChartsFragment.newInstance();
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
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, ExpenseFragment.newInstance());
        ft.commit();

        addAdapter = new AddExpenseAdapter();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new AuthValidationService(this);

        if(!TokenHolder.isLogged(getApplicationContext())){
            showLogin();
        }

    }

    /**
     * onResume
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        showLogin();
    }

    /**
     * show login Activity
     */
    private void showLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /**
     * onCreateOptionsMenu
     * @param menu the menu
     *
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     *
     * @param item menuitem selected
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_add:
                Intent addOrEdit = new Intent(this, AddOrEditActivity.class);
                addOrEdit.putExtra(getResources().getString(R.string.transaction_adapter_key), addAdapter);
                startActivity(addOrEdit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void authVerification(boolean isLogged) {
        if(!isLogged)
            showLogin();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
