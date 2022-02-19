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

public class orders extends AppCompatActivity {

    private RecyclerView view;
    public static Button btn;
    private int[] itemNumber;
    private int[] orderNumber;
    private int[] price;
    private int[] numberOfOrders;
    private int[] status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        view = findViewById(R.id.ordersView);
        view.setLayoutManager(new LinearLayoutManager(this));
        getOrder();
    }



    public void getOrder(){


        Intent intent = getIntent();
        user obj = (user) intent.getSerializableExtra("userObject");

        String url = "http://10.0.2.2/hotel-system/getOrders.php?id="+obj.getUserID();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            itemNumber = new int [response.length()];
                            orderNumber = new int [response.length()];
                            price = new int [response.length()];
                            numberOfOrders = new int [response.length()];
                            status = new int [response.length()];


                            for (int i=0;i<response.length();i++) {
                                JSONObject obj = response.getJSONObject(i);
                                itemNumber[i] = obj.getInt("itemNumber");
                                orderNumber[i] = obj.getInt("orderNumber");
                                price[i] = obj.getInt("price");
                                numberOfOrders[i] = obj.getInt("numberOfOrders");
                                status[i] = obj.getInt("isAccepted");
                            }
                            adapter3 adapter1 = new adapter3(orders.this,itemNumber,orderNumber, price,numberOfOrders,status);
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


}