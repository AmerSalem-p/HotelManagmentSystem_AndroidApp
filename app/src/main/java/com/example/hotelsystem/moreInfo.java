package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.user;

public class moreInfo extends AppCompatActivity {

    private TextView roomNumberTemp;
    private TextView floorTemp;
    private TextView numberOfBedsTemp;
    private TextView typeOfBedsTemp;
    private TextView priceTemp;
    private ImageView imageTemp;
    private Button btn;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;


    private int price;
    private String numberOfBeds;
    private int roomNumber;
    private String floor;
    private String typeofBeds;
    private String image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        user obj = (user) intent.getSerializableExtra("userObject");

        if(obj.getIsReceptionist()==0)
            setContentView(R.layout.activity_more_info);
        else
            setContentView(R.layout.activity_more_info2);
        imageTemp = findViewById(R.id.imageView);
        list = findViewById(R.id.roomInfoList);
        btn = findViewById(R.id.reserveRoom);

        roomNumber = intent.getIntExtra("roomNumber",0);
        price = intent.getIntExtra("price",0);
        floor = intent.getStringExtra("floor");
        numberOfBeds = intent.getStringExtra("numberOfBeds");
        typeofBeds = intent.getStringExtra("typeOfBeds");
        image1 = intent.getStringExtra("image1");


        String imagePath = "@drawable/"+image1;
        int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
        imageTemp.setImageResource(imageResource);

        arrayList = new ArrayList<String>();
        arrayList.add("Room number : "+roomNumber);
        arrayList.add("Floor Level : " +floor);
        arrayList.add("Number of beds : "+numberOfBeds);
        arrayList.add("Type of beds : "+typeofBeds);
        arrayList.add("price / Night : "+price+" $");




        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        list.setAdapter(adapter);
    }

    public void btn_onClick_reserveRoom(View view){
        Intent intent = getIntent();
        user obj = (user)intent.getSerializableExtra("userObject");
        Log.d("room",roomNumber+"");
        if(obj.getIsReceptionist()==0)
            reserveRoom(obj);
        else
            editRoom();

    }
    public void reserveRoom(user obj)
    {

        Intent intent = new Intent(this,reserveRoom.class);
        intent.putExtra("price",price);
        intent.putExtra("roomNumber",roomNumber);
        intent.putExtra("userObject",obj);
        startActivity(intent);
    }
    public void editRoom()
    {
        Intent intent = new Intent(this,EditRoomActivity.class);
        intent.putExtra("price",price+"");
        intent.putExtra("floor",floor);
        intent.putExtra("roomNumber",roomNumber+"");
        intent.putExtra("numberOfBeds",numberOfBeds);
        intent.putExtra("image1",image1);
        startActivity(intent);
    }
}