/**
 * The sign up activity
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.activites;


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
import ch.heig.cashflow.models.User;
import ch.heig.cashflow.network.services.LoginService;
import ch.heig.cashflow.network.services.SignupService;

public class SignupActivity extends AppCompatActivity implements LoginService.Callback {

    private static final String TAG = SignupActivity.class.getSimpleName();

    @BindView(R.id.input_firstname)
    EditText firstnameText;
    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginButton;

    /**
     * On activity create
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    /**
     * Try to signup
     */
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        String firstname = firstnameText.getText().toString();
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        User user = new User(firstname, name, email, password);

        new SignupService(this, user);
    }


    /**
     * Success signup
     */
    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }


    /**
     * Signup failed
     */
    public void onSignupFailed() {
        Toast.makeText(this, getString(R.string.error_signup),
                Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }


    /**
     * Validate signup informations
     * @return boolean
     */
    public boolean validate() {
        boolean valid = true;

        String firstname = firstnameText.getText().toString();
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (firstname.isEmpty() || firstname.length() < 3) {
            firstnameText.setError(getString(R.string.name_error));
            valid = false;
        } else {
            firstnameText.setError(null);
        }

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError(getString(R.string.name_error));
            valid = false;
        } else {
            nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.email_error));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            passwordText.setError(getString(R.string.password_error));
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError(getString(R.string.password2_error));
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

    /**
     * On login finished
     *
     * @param isLogged The user logged flag
     */
    @Override
    public void loginFinished(boolean isLogged) {
        if (isLogged) {
            onSignupSuccess();
        } else {
            onSignupFailed();
        }
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
