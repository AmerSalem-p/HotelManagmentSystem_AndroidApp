package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import model.MySingleton;
import model.user;

public class reservations extends AppCompatActivity {

    private RecyclerView view;
    public static Button btn;
    private int[] reservationID;
    private int[] customerID;
    private int[] roomNumber;
    private String[] reservationDate;
    private String[] leaveDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        view = findViewById(R.id.reservations_recycler);
        view.setLayoutManager(new LinearLayoutManager(this));
        btn = (Button) findViewById(R.id.cancel);
        getReservations();
    }


    private void getReservations(){


            Intent intent = getIntent();
            user obj = (user) intent.getSerializableExtra("userObject");


        String url = "http://10.0.2.2/hotel-system/getReservations.php?id=" + obj.getUserID();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            reservationID = new int[response.length()];
                            customerID = new int [response.length()];
                            roomNumber = new int [response.length()];
                            reservationDate = new String[response.length()];
                            leaveDate = new String[response.length()];

                            for (int i=0;i<response.length();i++) {
                                JSONObject obj = response.getJSONObject(i);

                            reservationID[i] = obj.getInt("reservationID");
                            customerID[i] = obj.getInt("customerID");
                            roomNumber[i] = obj.getInt("roomNumber");
                            reservationDate[i] = obj.getString("reservationDate");
                            leaveDate[i] = obj.getString("leaveDate");


                            }

                            adapter2 adapter1 = new adapter2(reservations.this,reservationID,customerID, roomNumber,reservationDate,leaveDate);
                            view.setAdapter(adapter1);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



    }


    public void btn_Home(View view){

        Intent intent = new Intent(this,customerFunctions.class);
        intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);

    }



}