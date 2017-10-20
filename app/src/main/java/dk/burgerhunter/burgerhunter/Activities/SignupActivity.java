package dk.burgerhunter.burgerhunter.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import dk.burgerhunter.burgerhunter.Helper.BlurBuilder;
import dk.burgerhunter.burgerhunter.R;
import dk.burgerhunter.burgerhunter.Volley.RegisterUser;
import dk.burgerhunter.burgerhunter.Volley.VolleySingleton;

/**
 * Created by SidonKK on 28/05/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;

    Button _signUpButton;

    TextView _loginLink;

    Response.Listener<String> listener;

    JSONObject _jsonObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        _nameText = (EditText) findViewById(R.id.register_name);
        _emailText = (EditText) findViewById(R.id.register_email);
        _passwordText = (EditText) findViewById(R.id.register_password);

        _signUpButton = (Button) findViewById(R.id.register_button);
        _loginLink = (TextView) findViewById(R.id.already_a_user_button);

        _signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {signUp();}
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition  (R.anim.left_slide_out, R.anim.left_slide_in);
            }
        });

        blurBackground();

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

    }  //handles all buttons references and listeners

    public void signUp() {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            return;
        }

        _signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        RegisterUser registerUser = new RegisterUser(name, email, password, listener);
        VolleySingleton.getInstance(this).addToRequestQueue(registerUser);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            boolean b = _jsonObject.getBoolean("error");
                            if (!b) {
                                onSignUpSuccess();
                            } else {
                                onSignUpFailed(_jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }, 2000);
    }  //handles sigup, checking if user exsist and other infomation is missing

    public void onSignUpSuccess() {
        _signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
        overridePendingTransition  (R.anim.left_slide_out, R.anim.left_slide_in);

    }  // If sigup success lest the user login

    public void onSignUpFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        _signUpButton.setEnabled(true);
    } // If sigup fails user gets promtet a message/toast that the sigup failed and why

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    } //validation check if the information/username/password has been used or are missing characters

    public void blurBackground(){
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Bitmap blurredBitmap = BlurBuilder.blur( SignupActivity.this, largeIcon );
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayout);
        linearLayout.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
    } //blur bracking image.

}
