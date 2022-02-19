package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.MySingleton;
import model.item;
import model.user;

public class requestRoomService extends AppCompatActivity {


    private ImageView imageView;
    private TextView txt1;
    private TextView txt2;
    private Spinner spinner;
    private Spinner spinner2;
    private user userObj;
    private item itemObj;
    private int totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_room_service);
        imageView = findViewById(R.id.foodImage);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        spinner = findViewById(R.id.numOfOrders);
        spinner2 = findViewById(R.id.orderRoomNumber);
        populateSpinner();
        fillInfo();
        populateSpinner2();
    }

    private void fillInfo(){

        Intent intent = getIntent();
        userObj = (user)intent.getSerializableExtra("userObject");
        itemObj = (item)intent.getSerializableExtra("item");


        String imagePath = "@drawable/"+itemObj.getImage();
        int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
        imageView.setImageResource(imageResource);


        txt1.setText("Order : "+itemObj.getItemName());
        txt2.setText("price : "+itemObj.getPrice());


    }


    private void populateSpinner() {
        List<String> filter = new ArrayList<>();
        filter.add("Number of orders...");
        filter.add("1");
        filter.add("2");
        filter.add("3");
        filter.add("4");
        filter.add("5");
        filter.add("6");
        filter.add("7");
        filter.add("8");
        filter.add("9");
        filter.add("10");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filter) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner.setAdapter(adapter);
    }

    private void populateSpinner2(){


        List<String> filter = new ArrayList<>();
        filter.add("Room Number...");

        String url = "http://10.0.2.2/hotel-system/isCheckedIn.php?id=" + userObj.getUserID();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                            JSONObject objTemp = response.getJSONObject(i);
                            filter.add(""+objTemp.getInt("roomNumber"));

                            }
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


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filter) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner2.setAdapter(adapter);

    }

    public void btn_order(View view) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(requestRoomService.this);
        totalPrice = itemObj.getPrice()*Integer.parseInt(spinner.getSelectedItem().toString());
        builder1.setMessage("Order : "+itemObj.getItemName()+"\n"+"Price : "+totalPrice);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "order",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String url = "http://10.0.2.2/hotel-system/order.php";

                        StringRequest stringRequest = new StringRequest
                                (Request.Method.POST, url,
                                        new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {


                                               Intent intent = new Intent(requestRoomService.this,roomService.class);
                                               startActivity(intent);
                                               finish();

                                                try {
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                Date c = Calendar.getInstance().getTime();

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                String formattedDate = df.format(c);

                                params.put("roomNumber", spinner2.getSelectedItem().toString()+"");
                                params.put("itemNumber", String.valueOf(itemObj.getItemNumber()));
                                params.put("price", String.valueOf(totalPrice));
                                params.put("orderDate",formattedDate);
                                params.put("numberOfOrders",spinner.getSelectedItem().toString());
                                params.put("customerID",String.valueOf(userObj.getUserID()));

                                return params;
                            }
                        };


                        MySingleton.getInstance(requestRoomService.this).addToRequestQueue(stringRequest);






                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
}