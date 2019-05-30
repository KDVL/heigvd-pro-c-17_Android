/**
 * The signin activity
 *
 * @author Kevin DO VALE
 * @version 1.0
 */
 
package ch.heig.cashflow.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.network.services.LoginService;

public class LoginActivity extends AppCompatActivity implements LoginService.Callback {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signupButton;

    ProgressDialog progressDialog;

    /**
     * onCreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }


    /**
     * called to log user
     */
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.authentification));
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //call Login service
        new LoginService(this, email, password);
    }


    /**
     * login success
     */
    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    /**
     * login failed
     */
    public void onLoginFailed() {
        Toast.makeText(this, getString(R.string.error_signin),
                Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    /**
     * called to log user
     */
    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.email_error));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.length() < 6) {
            passwordText.setError(getString(R.string.password_error));
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    /**
     * login is finished
     */
    @Override
    public void loginFinished(boolean isLogged) {

        progressDialog.dismiss();
        if (isLogged) {
            onLoginSuccess();
        } else {
            onLoginFailed();
        }
    }

    /**
     * @return the Application context
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
}
