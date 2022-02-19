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

public class removeCustomer extends AppCompatActivity {
    private EditText customerID;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_customer);
        customerID = findViewById(R.id.customerid);
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String customer = customerID.getText().toString();


         data = URLEncoder.encode("customerID","UTF-8") +"=" + URLEncoder.encode(customer,"UTF-8");


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

    public void RemoveCustomerOnCLick(View view) {
        String resturl = "http://10.0.2.2/hotel-system/removeCustomer.php";
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 123);
        }
        else {
            SendPostRequest runner = new SendPostRequest();
            runner.execute(resturl);
        }
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
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
            Toast.makeText(removeCustomer.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}