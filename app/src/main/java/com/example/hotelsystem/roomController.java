package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.MySingleton;
import model.room;
import model.user;

public class roomController extends AppCompatActivity {

    private RecyclerView recycleView;
    private room rooms[];
    private String[] numOfBeds;
    private int[] ids;
    private String[] type;
    private int[] price;
    private Spinner filterSpinner;
    private EditText filterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_controller1);
        recycleView = findViewById(R.id.room_recycler);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        filterSpinner = findViewById(R.id.filterSpinner);
        filterValue = findViewById(R.id.filterValue);

        populateSpinner();

        String url = "http://10.0.2.2/hotel-system/roomInfo.php";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            rooms = new room[response.length()];
                            numOfBeds = new String[response.length()];
                            ids = new int[response.length()];
                            type = new String[response.length()];
                            price = new int[response.length()];
                            for (int i = 0; i < response.length(); i++) {

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

                                String imagePath = "@drawable/" + rooms[i].getImage1();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                ids[i] = imageResource;
                                type[i] = rooms[i].getTypeOfBeds();
                                price[i] = rooms[i].getPrice();


                            }


                            Intent intent = getIntent();
                            user obj = (user) intent.getSerializableExtra("userObject");


                            adapter adapter1 = new adapter(roomController.this, numOfBeds, ids, type, price, rooms, obj);
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

    private void populateSpinner() {
        List<String> filter = new ArrayList<>();
        filter.add("Select Category...");
        filter.add("number Of Beds");
        filter.add("floor");
        filter.add("price");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filter) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
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
        filterSpinner.setAdapter(adapter);
    }

    public void btn_Filter(View view) {
        String attr1 = filterSpinner.getSelectedItem().toString().replaceAll(" ","");
        String attr2 = filterValue.getText().toString();
        String url = "http://10.0.2.2/hotel-system/roomInfo.php?"+attr1+"="+attr2;

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

                            adapter adapter1 = new adapter(roomController.this,numOfBeds, ids, type, price,rooms,obj);
                            recycleView.setAdapter(adapter1);


                        } catch (JSONException e) {
                            Toast.makeText(roomController.this,"No rooms found",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(roomController.this);
                        builder1.setMessage("No rooms found with the selected filter.");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    public void btn_showAll(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}



