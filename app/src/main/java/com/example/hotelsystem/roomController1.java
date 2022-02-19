package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.MySingleton;
import model.room;
import model.user;

public class roomController1 extends AppCompatActivity {

    private RecyclerView recycleView;
    private room rooms[];
    private String[] numOfBeds;
    private int[] ids;
    private String[] type;
    private int[] price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        user userObj=(user)intent.getSerializableExtra("userObject");
        if(userObj.getIsReceptionist()==0)
            setContentView(R.layout.activity_room_controller);
        else
            setContentView(R.layout.activity_room_receptionist);

        recycleView = findViewById(R.id.room_recycler);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://10.0.2.2/hotel-system/receptionistRoomInfo.php";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            rooms = new room[response.length()];
                            numOfBeds = new String[response.length()];
                            ids = new int[response.length()];
                            type = new String[response.length()];
                            price = new int[response.length()];
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                rooms[i] = new room();
                                rooms[i].setRoomNumber(obj.getInt("roomNumber"));
                                rooms[i].setFloor(obj.getString("floor"));
                                rooms[i].setNumberOfBeds(obj.getString("numberOfBeds"));
                                rooms[i].setTypeOfBeds(obj.getString("typeOfBeds"));
                                rooms[i].setPrice(obj.getInt("price"));
                                rooms[i].setSize(obj.getString("size"));
                                rooms[i].setImage1(obj.getString("image1"));

                                numOfBeds[i] = rooms[i].getNumberOfBeds();

                                String imagePath = "@drawable/"+rooms[i].getImage1();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                ids[i] =imageResource;
                                type[i] = rooms[i].getTypeOfBeds();
                                price[i] =rooms[i].getPrice();


                            }


                                Intent intent = getIntent();
                                user obj= (user)intent.getSerializableExtra("userObject");



                            adapter adapter1 = new adapter(roomController1.this,numOfBeds, ids, type, price,rooms,obj);
                            recycleView.setAdapter(adapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void btnGetReservedRooms(View view) {
        Button btn=findViewById(R.id.btnGetUnreserved);
        btn.setBackgroundColor(0xFF3700B3);
        Button btn1=findViewById(R.id.btnGetReserved);
        btn1.setBackgroundColor(0xFF000000);
        String url="http://10.0.2.2/hotel-system/receptionistRoomInfo.php?isReserved=1";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            rooms = new room[response.length()];
                            numOfBeds = new String[response.length()];
                            ids = new int[response.length()];
                            type = new String[response.length()];
                            price = new int[response.length()];
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                rooms[i] = new room();
                                rooms[i].setRoomNumber(obj.getInt("roomNumber"));
                                rooms[i].setFloor(obj.getString("floor"));
                                rooms[i].setNumberOfBeds(obj.getString("numberOfBeds"));
                                rooms[i].setTypeOfBeds(obj.getString("typeOfBeds"));
                                rooms[i].setPrice(obj.getInt("price"));
                                rooms[i].setSize(obj.getString("size"));
                                rooms[i].setImage1(obj.getString("image1"));

                                numOfBeds[i] = rooms[i].getNumberOfBeds();

                                String imagePath = "@drawable/"+rooms[i].getImage1();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                ids[i] =imageResource;
                                type[i] = rooms[i].getTypeOfBeds();
                                price[i] =rooms[i].getPrice();


                            }


                            Intent intent = getIntent();
                            user obj= (user)intent.getSerializableExtra("userObject");



                            adapter adapter1 = new adapter(roomController1.this,numOfBeds, ids, type, price,rooms,obj);
                            recycleView.setAdapter(adapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public void btnGetUnreservedRooms(View view) {
        Button btn=findViewById(R.id.btnGetUnreserved);
        btn.setBackgroundColor(0xFF000000);
        Button btn1=findViewById(R.id.btnGetReserved);
        btn1.setBackgroundColor(0xFF3700B3);
        String url="http://10.0.2.2/hotel-system/receptionistRoomInfo.php?isReserved=0";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            rooms = new room[response.length()];
                            numOfBeds = new String[response.length()];
                            ids = new int[response.length()];
                            type = new String[response.length()];
                            price = new int[response.length()];
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                rooms[i] = new room();
                                rooms[i].setRoomNumber(obj.getInt("roomNumber"));
                                rooms[i].setFloor(obj.getString("floor"));
                                rooms[i].setNumberOfBeds(obj.getString("numberOfBeds"));
                                rooms[i].setTypeOfBeds(obj.getString("typeOfBeds"));
                                rooms[i].setPrice(obj.getInt("price"));
                                rooms[i].setSize(obj.getString("size"));
                                rooms[i].setImage1(obj.getString("image1"));

                                numOfBeds[i] = rooms[i].getNumberOfBeds();

                                String imagePath = "@drawable/"+rooms[i].getImage1();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                ids[i] =imageResource;
                                type[i] = rooms[i].getTypeOfBeds();
                                price[i] =rooms[i].getPrice();


                            }


                            Intent intent = getIntent();
                            user obj= (user)intent.getSerializableExtra("userObject");



                            adapter adapter1 = new adapter(roomController1.this,numOfBeds, ids, type, price,rooms,obj);
                            recycleView.setAdapter(adapter1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}