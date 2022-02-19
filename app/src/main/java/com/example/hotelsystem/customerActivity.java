package com.example.hotelsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import model.user;

public class customerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }
    public void btncustInfo(View view) {
        Intent temp = getIntent();
        user obj = (user) temp.getSerializableExtra("userObject");


        Intent intent = new Intent(customerActivity.this, customerController.class);
        intent.putExtra("userObject",obj);
        startActivity(intent);

    }

    public void onClickRemove(View view) {
        Intent intent = new Intent(customerActivity.this, removeCustomer.class);
        startActivity(intent);
    }



    public void addnewcustmerButton(View view) {
        Intent intent = new Intent(customerActivity.this, addNewCustommer.class);
        startActivity(intent);
    }
}