package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.time.format.DateTimeFormatter;

import model.MySingleton;
import model.reservation;
import model.room;
import model.user;

public class CheckActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private reservation []reservations;
    private int []imageIds;
    private EditText edtCustomerId;
    private EditText edtRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        recyclerView=findViewById(R.id.check_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        edtCustomerId=findViewById(R.id.edtCustomerId);
        edtRoomNumber=findViewById(R.id.edtRoomNumebr);



    }

    public void btnCheck(View view) {
        String customerId=edtCustomerId.getText().toString();
        String roomNumber=edtRoomNumber.getText().toString();
        String url = "http://10.0.2.2/hotel-system/check.php";
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
                            reservations = new reservation[response.length()];
                            imageIds = new int[response.length()];
                            boolean []checkedIn=new boolean[response.length()];
                            boolean []checkedOut=new boolean[response.length()];
                            String customerName=response.getJSONObject(0).getString("customerName");
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                reservations[i] = new reservation();
                                reservations[i].setRoomNumber(obj.getInt("roomNumber"));
                                reservations[i].setCustomerId(obj.getInt("customerID"));
                                reservations[i].setReservavtionId(obj.getInt("reservationID"));
                                checkedIn[i]=obj.getBoolean("checkedIn");
                                checkedOut[i]=obj.getBoolean("checkedOut");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    reservations[i].setLeaveDate(LocalDate.parse(obj.getString("leaveDate")));
                                    reservations[i].setReserationDate(LocalDate.parse(obj.getString("reservationDate")));
                                }

                                String imagePath = "@drawable/"+obj.getString("image");
                                Toast.makeText(getApplicationContext(),imagePath,Toast.LENGTH_SHORT).show();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                imageIds[i] =imageResource;



                            }


                            CheckAdapter checkAdapter = new CheckAdapter(CheckActivity.this,reservations,imageIds,customerName,checkedIn,checkedOut);
                            recyclerView.setAdapter(checkAdapter);


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