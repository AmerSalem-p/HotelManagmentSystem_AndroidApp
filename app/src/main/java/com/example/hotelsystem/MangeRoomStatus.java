package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.MySingleton;
import model.room;
import model.user;
public class MangeRoomStatus extends AppCompatActivity {

    private RadioButton radioReady;
    private RadioButton radioUnderCleaning;
    private RadioButton radioUnderMaintenance;
    private EditText edtRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mange_room_status);
        edtRoomNumber = findViewById(R.id.roomNumber);
        radioReady=findViewById(R.id.ready);
        radioUnderMaintenance=findViewById(R.id.underMaintenance);
        radioUnderCleaning=findViewById(R.id.underCleaning);
        radioReady.setChecked(true);



    }


    public void btnMangeRoomServiceOnClick(View view) {
        String url = "http://10.0.2.2/hotel-system/manageRoomStatus.php";

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Intent intent=new Intent(MangeRoomStatus.this,receptionFunctions.class);
                                startActivity(intent);
                            }


                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("roomNumber", edtRoomNumber.getText()+"");
                if(checkedRadio()!=-1)
                    params.put("roomStatus",checkedRadio()+"");
                else
                    cancel();
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public int checkedRadio()
    {
        if(radioReady.isChecked())
            return 0;
        else if(radioUnderCleaning.isChecked())
            return 1;
        else if(radioUnderMaintenance.isChecked())
            return 2;
        return -1;
    }
    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        RadioButton r1 = (RadioButton) findViewById(R.id.underCleaning);
        RadioButton r2 = (RadioButton) findViewById(R.id.underMaintenance);
        RadioButton r3 = (RadioButton) findViewById(R.id.ready);
        switch (v.getId()) {

            case R.id.underCleaning:

                if (checked) {
                    r2.setChecked(false);
                    r3.setChecked(false);
                }
                break;

            case R.id.underMaintenance:
                if (checked) {
                    r1.setChecked(false);
                    r3.setChecked(false);
                }
                break;

            case R.id.ready:
                if (checked) {
                    r1.setChecked(false);
                    r2.setChecked(false);
                }
                break;

        }

    }
    }


