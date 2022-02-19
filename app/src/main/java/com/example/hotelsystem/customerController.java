package com.example.hotelsystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.MySingleton;
import model.user;

public class customerController extends AppCompatActivity {

    private RecyclerView recycleView;
    private user users[];
    private String[] name;
    private int[] ids;
    private String[] gender;
    private String[] phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_controller);
        recycleView = findViewById(R.id.customer_recycler);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        String url = "http://10.0.2.2/hotel-system/customerInfo.php";
       // Toast.makeText(customerController.this ,"name is" , Toast.LENGTH_SHORT).show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {
                     //Toast.makeText(this,"name is" , Toast.LENGTH_SHORT).show();

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                         //   Toast.makeText(customerController.this ,"name is" , Toast.LENGTH_SHORT).show();
                            users = new user[response.length()];
                            name = new String[response.length()];
                            ids = new int[response.length()];
                            gender = new String[response.length()];
                            phone = new String[response.length()];
                            for (int i = 0; i < response.length(); i++){

                                JSONObject obj = response.getJSONObject(i);
                                users[i] = new user();
                                users[i].setUserID(obj.getInt("userID"));
                                users[i].setName(obj.getString("Name"));
                                users[i].setGender(obj.getString("Gender"));
                                users[i].setPhone(obj.getString("Phone"));
                                 name [i] = users[i].getName();
                                 ids[i] = users[i].getUserID();
                                 gender[i] = users[i].getGender();
                                 phone[i] = users[i].getPhone();

                            }

                            Intent intent = getIntent();
                            user obj= (user)intent.getSerializableExtra("customerObject1");


                            adapter4 adpter = new adapter4(customerController.this,name,ids,gender,phone);

                            recycleView.setAdapter(adpter);
                          //  Toast.makeText(customerController.this ,"name is" + name[0].toString(), Toast.LENGTH_SHORT).show();

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
}