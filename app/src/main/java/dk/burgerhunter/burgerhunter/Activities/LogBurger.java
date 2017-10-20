package dk.burgerhunter.burgerhunter.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import dk.burgerhunter.burgerhunter.R;
import dk.burgerhunter.burgerhunter.Volley.RegisterBurger;
import dk.burgerhunter.burgerhunter.Volley.RegisterUser;
import dk.burgerhunter.burgerhunter.Volley.VolleySingleton;

/**
 * Created by SidonKK on 04/07/2017.
 */

public class LogBurger extends AppCompatActivity {

    public String encoded_string, image_name;
    public Bitmap image_bitmap;
    public Uri url_file;
    public File image_file;
    Button take_image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_burger);


        take_image = (Button) findViewById(R.id.take_image);

        take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, url_file);
                startActivityForResult(i, 10);
            }
        });



    }

    private void getFileUri() {

        image_file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name);

        url_file = Uri.fromFile(image_file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10 && requestCode == RESULT_OK){
            new Encode_image().execute();
        }
    }

    private  class Encode_image extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            image_bitmap = BitmapFactory.decodeFile(url_file.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }

    private void makeRequest() {

        // TODO: Implement your own signup logic here.
        //RegisterBurger registerBurger = new RegisterBurger(name, email, password, listener);
        //VolleySingleton.getInstance(this).addToRequestQueue(registerBurger);


    }
}
