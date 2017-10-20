package dk.burgerhunter.burgerhunter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import dk.burgerhunter.burgerhunter.Helper.BlurBuilder;
import dk.burgerhunter.burgerhunter.R;
import dk.burgerhunter.burgerhunter.Volley.ForgotPassword;
import dk.burgerhunter.burgerhunter.Volley.LoginUser;
import dk.burgerhunter.burgerhunter.Volley.VolleySingleton;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";


    TextView forgetMail;

    Button submitButton;

    Response.Listener<String> listener;

    JSONObject _jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgetMail = (TextView) findViewById(R.id.forget_email);
        submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 submitRequest();
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


    }

    public void submitRequest() {
        Log.d(TAG, "ForgotPassword");

        if (!validate()) {
            return;
        }

        submitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Requesting password reset...");
        progressDialog.show();

        String email = forgetMail.getText().toString();

        ForgotPassword forgotPassword = new ForgotPassword(email, listener);
        VolleySingleton.getInstance(this).addToRequestQueue(forgotPassword);

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            if(_jsonObject != null) {
                                boolean b = _jsonObject.getBoolean("error");
                                if (!b) {
                                    onRequestSuccess();

                                } else {
                                    onRequestFailed(_jsonObject.getString("message"));
                                }
                            }else{
                                onRequestFailed("Wrong Password or Email");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, 2000);
    }  //handles all login logic, checing if password or username are wrong, and sending server request vis volley

    void onRequestFailed(String message){
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            submitButton.setEnabled(true);
    }

    void onRequestSuccess() {
        Toast.makeText(getBaseContext(), "Reset introduction sent", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void blurBackground(){
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Bitmap blurredBitmap = BlurBuilder.blur( ForgotPasswordActivity.this, largeIcon );
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLayout);
        linearLayout.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
    }

    public boolean validate() {
        boolean valid = true;


        String email = forgetMail.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgetMail.setError("enter a valid email address");
            valid = false;
        } else {
            forgetMail.setError(null);
        }


        return valid;
    }
}
