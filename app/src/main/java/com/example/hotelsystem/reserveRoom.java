package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.MySingleton;
import model.user;

public class reserveRoom extends AppCompatActivity {

    private Spinner spinner;
    private EditText date1;
    private EditText date2;
    private TextView totalPrice;
    private Button reserve;
    private Button confirm;
    private String temp1;
    private String temp2;
    private Date d1 = null;
    private Date d2 = null;
    private user obj;
    private Date dateTemp1=null;
    private Date dateTemp2=null;
    private boolean reserved = false;
    private int roomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);

        spinner = findViewById(R.id.spinner);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        totalPrice = findViewById(R.id.totalPrice);
        reserve = findViewById(R.id.reserve);
        confirm = findViewById(R.id.confirm);
        reserve.setEnabled(false);
        populateSpinner();
        date1.setFocusable(false);
        date2.setFocusable(false);

        Intent intentTemp = getIntent();
        obj = (user) intentTemp.getSerializableExtra("userObject");
        roomNumber = intentTemp.getIntExtra("roomNumber", 0);



        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                chooseDate1();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                chooseDate2();
            }
        });



    }

    public void btn_reserve(View view){

        Intent intentTemp = getIntent();
        obj = (user) intentTemp.getSerializableExtra("userObject");
        roomNumber = intentTemp.getIntExtra("roomNumber", 0);

        String url = "http://10.0.2.2/hotel-system/reservation.php";

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                Intent intent = new Intent(getApplicationContext(), reservations.class);
                                intent.putExtra("userObject", obj);
                                startActivity(intent);

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

                params.put("customerID", obj.getUserID()+"");
                params.put("roomNumber", roomNumber+"");
                params.put("reservationDate", String.valueOf(temp1));
                params.put("leaveDate", String.valueOf(temp2));

                return params;
            }
        };


        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void btn_calculateFullPrice(View view){

        reserved = false;
        reserve.setEnabled(false);
        temp1 = date1.getText().toString();
        temp2 = date2.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            d1 = simpleDateFormat.parse(temp1);
            d2 = simpleDateFormat.parse(temp2);

        } catch (ParseException e) {
        }


        Intent intentTemp = getIntent();
        obj = (user) intentTemp.getSerializableExtra("userObject");
        int roomNumber = intentTemp.getIntExtra("roomNumber",0);

        String url = "http://10.0.2.2/hotel-system/reservationCheck.php?roomNumber="+roomNumber;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            for (int i=0;i<response.length();i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String dateString1 = obj.getString("reservationDate");
                                String dateString2 = obj.getString("leaveDate");
                                try {
                                    dateTemp1=new SimpleDateFormat("yyyy-MM-dd").parse(dateString1);
                                    dateTemp2=new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }



                                if(dateString1.compareTo(temp1.toString())==0){
                                    reserved = true;
                                    break;
                                }

                                if(d1.compareTo(dateTemp1)>0 && d1.compareTo(dateTemp2)<0){
                                    reserved = true;
                                    break;
                                }

                                if(d2.compareTo(dateTemp1)>0 && d2.compareTo(dateTemp2)<0){
                                    reserved = true;
                                    break;
                                }

                                if(d1.compareTo(dateTemp1)>0 && d2.compareTo(dateTemp2)<0 ){
                                    reserved = true;
                                    break;
                                }

                            }

                            if(reserved == false){
                                reserve.setEnabled(true);
                            }
                            else{
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(reserveRoom.this);
                                builder1.setMessage("Cant Reserve Room, its already reserved during the selected date");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "OKAY",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }


                        } catch (JSONException e) {
                            reserve.setEnabled(true);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        reserve.setEnabled(true);

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        Intent intent = getIntent();
        int price = intent.getIntExtra("price", 0);

        long difference = d2.getTime() - d1.getTime();
        long Days = difference / (24 * 60 * 60 * 1000);
        totalPrice.setText("Total Price : " + Days * price * Integer.parseInt(spinner.getSelectedItem().toString()) + " $");


    }

    private void chooseDate1() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year, month, dayOfMonth);
                        String dateString = sdf.format(calendar.getTime());

                        date1.setText(dateString); // set the date
                    }
                }, year, month, day); // set date picker to current date

        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void chooseDate2() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year, month, dayOfMonth);
                        String dateString = sdf.format(calendar.getTime());

                        date2.setText(dateString); // set the date
                    }
                }, year, month, day); // set date picker to current date

        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void populateSpinner() {
        List<String> numOfGuests = new ArrayList<>();
        numOfGuests.add("1");
        numOfGuests.add("2");
        numOfGuests.add("3");
        numOfGuests.add("4");
        numOfGuests.add("5");
        numOfGuests.add("6");
        numOfGuests.add("7");
        numOfGuests.add("8");
        numOfGuests.add("9");
        numOfGuests.add("10");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, numOfGuests);
        spinner.setAdapter(adapter);
    }
}