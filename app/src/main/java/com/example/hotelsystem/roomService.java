package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.MySingleton;
import model.item;
import model.user;

public class roomService extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);
    }

    public void btn_breakfast(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");


        Intent intent = new Intent(this,serviceView.class);
        intent.putExtra("userObject",obj);
        intent.putExtra("itemType","breakfast");
        startActivity(intent);
        finish();

    }
    public void btn_lunch(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");

        Intent intent = new Intent(this,serviceView.class);
        intent.putExtra("userObject",obj);
        intent.putExtra("itemType","lunch");
        startActivity(intent);
        finish();
    }
    public void btn_dinner(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");

        Intent intent = new Intent(this,serviceView.class);
        intent.putExtra("userObject",obj);
        intent.putExtra("itemType","dinner");
        startActivity(intent);
        finish();
    }
    public void btn_drinks(View view){

        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");

        Intent intent = new Intent(this,serviceView.class);
        intent.putExtra("userObject",obj);
        intent.putExtra("itemType","drinks");
        startActivity(intent);
        finish();
    }





}