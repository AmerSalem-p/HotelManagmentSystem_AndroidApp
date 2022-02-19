package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import model.user;

public class receptionFunctions extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_functions);

    }

    private user returnUser()
    {
        Intent intent=getIntent();
        user user = (model.user) intent.getSerializableExtra("userObject");
        return user;
    }


    public void btn_logout(View view){

        Intent intent = new Intent(receptionFunctions.this, MainActivity.class);
        startActivity(intent);
    }


    public void btn_room(View view) {
        Intent intent=new Intent(receptionFunctions.this, roomController1.class);
        intent.putExtra("userObject",returnUser());
        startActivity(intent);
    }

    public void btnCheck(View view) {
        Intent intent = new Intent(this , CheckActivity.class );
        startActivity(intent);
    }
    public void resptionFunction(View view) {
        Intent intent = new Intent(receptionFunctions.this, customerActivity.class);
        startActivity(intent);
    }



    public void btnOnClickManageStatus(View view) {
        Intent in = new Intent(this, MangeRoomStatus.class);
        startActivity(in);
    }

    public void btnServices(View view) {
        Intent intent = new Intent(this, Services.class);
        startActivity(intent);
    }

    public void btnReservations(View view) {
        Intent intent = new Intent(this, ReservationsReceptionist.class);
        startActivity(intent);
    }
}