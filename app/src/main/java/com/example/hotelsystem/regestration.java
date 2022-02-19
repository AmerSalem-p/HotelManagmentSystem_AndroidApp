package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class regestration extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText gender;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        name = findViewById(R.id.txtname);
        phone = findViewById(R.id.txtPhone);
        gender = findViewById(R.id.txtGender);

        pass = findViewById(R.id.txtpass);

    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException{
        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String Gender = gender.getText().toString();

        String Pass = pass.getText().toString();

        String data = URLEncoder.encode("Name","UTF-8") +"=" + URLEncoder.encode(Name,"UTF-8");
        data += "&" + URLEncoder.encode("Phone","UTF-8") + "=" +URLEncoder.encode(Phone,"UTF-8");
        data += "&" + URLEncoder.encode("Gender","UTF-8") + "=" +URLEncoder.encode(Gender,"UTF-8");

        data += "&" + URLEncoder.encode("Password","UTF-8") + "=" +URLEncoder.encode(Pass,"UTF-8");

        String text = "";
        BufferedReader reader = null;

        try {
            URL url = new URL(restUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();
            //get server respnce
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line= reader.readLine())!=null){
                sb.append(line + "\n");
            }
            text = sb.toString();
        }
        catch (Exception e){
           e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return text;
    }

    public void btnRegestrationOnClick(View view) {
        String resturl = "http://10.0.2.2/hotel-system/signUp.php";
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 123);
        }
        else {
            SendPostRequest runner = new SendPostRequest();
            runner.execute(resturl);
        }
    }

    public class SendPostRequest extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... url){
            try{
                return processRequest(url[0]);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result){
            Toast.makeText(regestration.this, result, Toast.LENGTH_SHORT).show();
        }
    }


}