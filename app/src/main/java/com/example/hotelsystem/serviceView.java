package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

public class serviceView extends AppCompatActivity {
    private ListView list;
    private item[] items;
    private String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view);
        list = findViewById(R.id.items);

        Intent intent = getIntent();
        Type = intent.getStringExtra("itemType");
        getItems();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     Intent temp = getIntent();
                     user obj = (user)temp.getSerializableExtra("userObject");

                     Intent intent = new Intent(serviceView.this,requestRoomService.class);
                     intent.putExtra("userObject",obj);
                     intent.putExtra("item",items[position]);
                     startActivity(intent);
                     finish();

            }
        });




    }

    public void getItems(){
        String url = "http://10.0.2.2/hotel-system/getServices.php?type="+Type;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        items = new item[response.length()];
                        String[] textString = new String[response.length()];
                        int[] drawableIds = new int[response.length()];

                        try{

                            for(int i =0;i<response.length();i++){
                                JSONObject obj = response.getJSONObject(i);
                                items[i] = new item();
                                items[i].setItemNumber(obj.getInt("itemNumber"));
                                items[i].setItemName(obj.getString("itemName"));
                                items[i].setItemType(obj.getString("itemType"));
                                items[i].setPrice(obj.getInt("price"));
                                items[i].setImage(obj.getString("image"));

                                textString[i] = items[i].getItemName();
                                String imagePath = "@drawable/"+items[i].getImage();
                                int imageResource = getResources().getIdentifier(imagePath, null, getPackageName());
                                drawableIds[i] = imageResource;
                            }

                            customAdapter adapter = new customAdapter(serviceView.this,  textString, drawableIds);
                            list.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


}