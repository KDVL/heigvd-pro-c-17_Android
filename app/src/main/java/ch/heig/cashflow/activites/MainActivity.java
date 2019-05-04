package ch.heig.cashflow.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


import ch.heig.cashflow.adapters.ExpenseService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ch.heig.cashflow.R;
import ch.heig.cashflow.fragments.ExpenseFragment;
import ch.heig.cashflow.models.Earning;
import ch.heig.cashflow.models.Expense;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    public static final DecimalFormat df = new DecimalFormat("#.00");

    public ArrayList<Earning> earningsArrayList = null;
    public ArrayList<Expense> currentMonthExpensesArrayList = null;

    private ExpenseService expenceService = null;

    private float earnings = 0.f;
    private float expenses = 0.f;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = ExpenseFragment.newInstance();
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenceService = new ExpenseService();
        currentMonthExpensesArrayList = expenceService.getAll().get("05");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, ExpenseFragment.newInstance());
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
    }

    public String getExpenses() {
        return df.format(expenses);
    }
}
