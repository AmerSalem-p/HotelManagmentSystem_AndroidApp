package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.MySingleton;

public class ReservationsReceptionist extends AppCompatActivity {
    private EditText edtCustomerId;
    private EditText edtRoomNumber;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_receptionist);
        edtCustomerId=findViewById(R.id.edtCustomerId);
        edtRoomNumber=findViewById(R.id.edtRoomNumber);
        recyclerView=findViewById(R.id.reservations_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void btnFindReservations(View view) {
        String customerId=edtCustomerId.getText().toString();
        String roomNumber=edtRoomNumber.getText().toString();

        String url="http://10.0.2.2/hotel-system/reservationReceptionist.php";

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
                        Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                        ArrayList<String> customerName = new ArrayList<>();
                        ArrayList<Integer> roomNumber = new ArrayList<>();
                        ArrayList<Integer> reservationId = new ArrayList<>();
                        ArrayList<String> checkedInDate = new ArrayList<>();
                        ArrayList<String> checkedOutDate = new ArrayList<>();
                        ArrayList<String> reservationDate = new ArrayList<>();
                        ArrayList<String> leaveDate = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_SHORT).show();
                                customerName.add(obj.getString("customerName"));
                                roomNumber.add(obj.getInt("roomNumber"));
                                reservationId.add(obj.getInt("reservationID"));
                                checkedInDate.add(obj.getString("checkedInDate"));
                                checkedOutDate.add(obj.getString("checkedOutDate"));
                                reservationDate.add(obj.getString("reservationDate"));
                                leaveDate.add(obj.getString("leaveDate"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        ReservationAdapter reservationAdapter = new ReservationAdapter(
                                customerName, roomNumber, reservationId, checkedInDate, checkedOutDate, reservationDate, leaveDate, ReservationsReceptionist.this);
                        recyclerView.setAdapter(reservationAdapter);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recyclerView.setAdapter(null);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
                }
