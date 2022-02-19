package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

import model.MySingleton;
import model.reservation;


public class Services extends AppCompatActivity {

    private EditText edtCustomerId;
    private EditText edtRoomNumber;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        edtCustomerId=findViewById(R.id.edtCustomerId);
        edtRoomNumber=findViewById(R.id.edtRoomNumber);
        recyclerView=findViewById(R.id.service_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getOrdersRequest();

    }

    public void btnFindServices(View view) {
        String customerId=edtCustomerId.getText().toString();
        String roomNumber=edtRoomNumber.getText().toString();

        String url="http://10.0.2.2/hotel-system/services.php";

        if(!customerId.isEmpty()&&!roomNumber.isEmpty())
            url=url+"?customerId="+customerId+"&roomNumber="+roomNumber;
        else if(!customerId.isEmpty())
            url=url+"?customerId="+customerId;
        else if(!roomNumber.isEmpty())
            url=url+"?roomNumber="+roomNumber;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<String> itemName=new ArrayList<>();
                            ArrayList<String> customerName=new ArrayList<>();
                            ArrayList<Integer> roomNumber=new ArrayList<>();
                            ArrayList<Integer> orderNumber=new ArrayList<>();
                            ArrayList<Integer> numOfOrders=new ArrayList<>();
                            ArrayList<Integer> price=new ArrayList<>();

                            for (int i=0 ; i<response.length() ;i++)
                            {
                                JSONObject obj=response.getJSONObject(i);
                                itemName.add(obj.getString("itemName"));
                                customerName.add(obj.getString("customerName"));
                                roomNumber.add(obj.getInt("roomNumber"));
                                numOfOrders.add(obj.getInt("numberOfOrders"));
                                orderNumber.add(obj.getInt("orderNumber"));
                                price.add(obj.getInt("price"));

                                ServiceAdapter adapter =
                                        new ServiceAdapter(itemName,customerName,roomNumber,orderNumber,numOfOrders,price,Services.this);
                                recyclerView.setAdapter(adapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(null);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void btnAccept(View view) {
    }

    public void btnReject(View view) {
    }

    public void getOrdersRequest()
    {


        String url="http://10.0.2.2/hotel-system/services.php";


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<String> itemName=new ArrayList<>();
                            ArrayList<String> customerName=new ArrayList<>();
                            ArrayList<Integer> roomNumber=new ArrayList<>();
                            ArrayList<Integer> orderNumber=new ArrayList<>();
                            ArrayList<Integer> numOfOrders=new ArrayList<>();
                            ArrayList<Integer> price=new ArrayList<>();

                                for (int i=0 ; i<response.length() ;i++)
                                {
                                    JSONObject obj=response.getJSONObject(i);
                                    itemName.add(obj.getString("itemName"));
                                    customerName.add(obj.getString("customerName"));
                                    roomNumber.add(obj.getInt("roomNumber"));
                                    numOfOrders.add(obj.getInt("numberOfOrders"));
                                    Toast.makeText(getApplicationContext(),numOfOrders.get(i).toString(),Toast.LENGTH_SHORT).show();
                                    orderNumber.add(obj.getInt("orderNumber"));
                                    price.add(obj.getInt("price"));


                                    ServiceAdapter adapter =
                                            new ServiceAdapter(itemName,customerName,roomNumber,orderNumber,numOfOrders,price,Services.this);
                                    recyclerView.setAdapter(adapter);
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(null);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}