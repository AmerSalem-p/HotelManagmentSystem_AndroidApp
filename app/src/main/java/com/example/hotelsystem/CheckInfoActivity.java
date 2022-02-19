package com.example.hotelsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.MySingleton;
import model.reservation;


public class CheckInfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnCheckedIn;
    private  Button btnCheckedOut;
    private ListView list;
    private String checkedIn;
    private String checkedOut;
    private String checkedInDate;
    private String checkedOutDate;
    private int roomStatus;
    ArrayList<String>arrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
        Intent intent =getIntent();
        imageView = findViewById(R.id.imageView);
        list = findViewById(R.id.checkInfoList);
        btnCheckedIn=findViewById(R.id.btnCheckIn);
        btnCheckedOut=findViewById(R.id.btnCheckOut);
        String roomNumber=intent.getStringExtra("roomNumber");
        String customerName=intent.getStringExtra("customerName");
        String image = intent.getStringExtra("image");
        requestGetInfo(roomNumber);


        imageView.setImageResource(Integer.valueOf(image));

        arrayList = new ArrayList<String>();
        arrayList.add("customer name : " +customerName);
        arrayList.add("Room number : "+roomNumber);






    }

    public void btnCheckIn(View view) {
        Intent intent =getIntent();
        String reservationId=intent.getStringExtra("reservationId");


        String url = "http://10.0.2.2/hotel-system/check.php";

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>()  {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("checkIn",checkedIn);
                        params.put("checkOut",checkedOut);
                        params.put("reservationId",reservationId);
                        return params;
                    }
                };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        finish();
        startActivity(getIntent());

    }

    public void btnCheckOut(View view) {

        Intent intent =getIntent();
        String reservationId=intent.getStringExtra("reservationId");


        String url = "http://10.0.2.2/hotel-system/check.php";

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>()  {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("checkIn",checkedIn);
                params.put("checkOut",checkedOut);
                params.put("reservationId",reservationId);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        finish();
        startActivity(getIntent());
    }

    public void requestGetInfo(String roomNumber)
    {
        String url = "http://10.0.2.2/hotel-system/check.php?roomNumber="+roomNumber;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);

                                checkedIn=obj.getBoolean("checkedIn")+"";
                                checkedOut=obj.getBoolean("checkedOut")+"";
                                checkedInDate=obj.getString("checkedInDate");
                                checkedOutDate=obj.getString("checkedOutDate");
                                roomStatus=Integer.valueOf(obj.getString("roomStatus"));


                            }

                            if (checkedIn.equals("true")&&checkedOut.equals("true"))
                                btnCheckedOut.setEnabled(false);
                            else if ((checkedIn.equals("true")&&checkedOut.equals("false"))||roomStatus!=0)
                                btnCheckedIn.setEnabled(false);

                            arrayList.add("checked in : "+checkedIn);
                            arrayList.add("checked out : "+checkedOut);
                            arrayList.add("checked in date : "+checkedInDate);
                            arrayList.add("checked out date : "+checkedOutDate);
                            ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
                            list.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}