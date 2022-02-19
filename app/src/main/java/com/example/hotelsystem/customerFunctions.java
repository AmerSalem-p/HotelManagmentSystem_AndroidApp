package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.MySingleton;
import model.user;

public class customerFunctions extends AppCompatActivity {


    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_functions);
        btn = findViewById(R.id.service);
        isCheckedIn();

    }


    public void btn_room(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");


        Intent intent = new Intent(customerFunctions.this, roomController.class);
        intent.putExtra("userObject",obj);
        startActivity(intent);
    }

    public void btn_logout(View view){
        Intent intent = new Intent(customerFunctions.this, MainActivity.class);
        startActivity(intent);

    }

    public void reservation_btn(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");

        Intent intent = new Intent(customerFunctions.this, reservations.class);
        intent.putExtra("userObject",obj);
        intent.setFlags(intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }

    public void btn_changInfo(View view){


        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");


        Intent intent = new Intent(customerFunctions.this, changeInfo.class);
        intent.putExtra("userObject",obj);
        startActivity(intent);
    }

    public void btn_roomService(View view){
        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");
        Intent intent = new Intent(this,roomService.class);
        intent.putExtra("userObject",obj);
        startActivity(intent);
    }

    public  void btn_orders(View view){
        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");
        Intent intent = new Intent(this,orders.class);
        intent.putExtra("userObject",obj);
        startActivity(intent);
    }

    public void isCheckedIn(){

        btn.setEnabled(false);

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");

        String url = "http://10.0.2.2/hotel-system/isCheckedIn.php?id=" + obj.getUserID();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject objTemp = response.getJSONObject(0);
                            btn.setEnabled(true);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}