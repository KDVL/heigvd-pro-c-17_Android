package ch.heig.cashflow.activites;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;


public class AddActivity extends AppCompatActivity {

     @BindView(R.id.input_categorie) Spinner categoriesSpinner;
     @BindView(R.id.input_description) EditText descriptionText;
     @BindView(R.id.input_price) EditText priceText;
     @BindView(R.id.btn_add)  Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        ButterKnife.bind(this);



    }
}