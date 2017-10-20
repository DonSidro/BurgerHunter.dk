package dk.burgerhunter.burgerhunter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import dk.burgerhunter.burgerhunter.Helper.BlurBuilder;
import dk.burgerhunter.burgerhunter.R;
import dk.burgerhunter.burgerhunter.Volley.LoginUser;
import dk.burgerhunter.burgerhunter.Volley.VolleySingleton;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGN_UP = 0;
    private static final int REQUEST_PASSWORD = 1;

    Button _loginButton;

    Button _signUpButton;

    TextView _forgotPasswordButton;

    EditText _emailText;
    EditText _passwordText;

    CheckBox _checkBox;

    Response.Listener<String> listener;

    JSONObject _jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _checkBox = (CheckBox) findViewById(R.id.checkBox);
        _loginButton = (Button) findViewById(R.id.email_sign_in_button);
        _signUpButton = (Button) findViewById(R.id.email_sign_up_button);
        _forgotPasswordButton = (TextView) findViewById(R.id.email_forgot_password_button);

        _emailText = (EditText) findViewById(R.id.login_email);
        _passwordText = (EditText) findViewById(R.id.login_password);


        checkAutoLogin();

        blurBackground();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {startIntent();}
        });

        _forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivityForResult(intent, REQUEST_PASSWORD);
                overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });

        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    _jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

    } //handles all buttons references and listeners

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        LoginUser registerUser = new LoginUser(email, password, listener);
        VolleySingleton.getInstance(this).addToRequestQueue(registerUser);

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            if(_jsonObject != null) {
                                boolean b = _jsonObject.getBoolean("error");
                                if (!b) {
                                    if (_checkBox.isChecked()) {
                                        getSharedPreferences("USER", MODE_PRIVATE)
                                                .edit()
                                                .putString("EMAIL", _emailText.getText().toString())
                                                .putString("PASSWORD", _passwordText.getText().toString())
                                                .putString("APIKEY", _jsonObject.getString("apiKey").toString())
                                                .apply();
                                    }
                                    onLoginSuccess();
                                } else {
                                    onLoginFailed(_jsonObject.getString("message"));
                                }
                            }else{
                                onLoginFailed("Wrong Password or Email");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, 2000);
    }  //handles all login logic, checing if password or username are wrong, and sending server request vis volley

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGN_UP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically

            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    } // if login success

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    } // if fail login

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (_emailText.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (_passwordText.getText().toString().isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void checkAutoLogin(){

        SharedPreferences pref = getSharedPreferences("USER",MODE_PRIVATE);
        String email = pref.getString("EMAIL", null);
        String password = pref.getString("PASSWORD", null);
        if (email != null || password != null) {
            onLoginSuccess();
        }
    }

    public void blurBackground(){
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Bitmap blurredBitmap = BlurBuilder.blur( LoginActivity.this, largeIcon );
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayout);
        linearLayout.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
    }

    public void startIntent(){
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGN_UP);
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }


}

