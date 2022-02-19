package com.example.hotelsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.MySingleton;
import model.room;
import model.user;


public class EditRoomActivity extends AppCompatActivity {
    private EditText edtPrice;
    private EditText edtFloor;
    private EditText edtNumberOfBeds;
    private EditText edtImage;
    private Spinner spinner;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        Intent intent=getIntent();
        roomNumber=intent.getStringExtra("roomNumber");
        edtPrice=findViewById(R.id.edtPrice);
        edtFloor=findViewById(R.id.edtFloor);
        edtNumberOfBeds=findViewById(R.id.edtNumberOfBeds);
        edtImage=findViewById(R.id.edtImage);
        spinner=findViewById(R.id.spinnerTypeOfBeds);
        edtPrice.setText(intent.getStringExtra("price"));
        edtFloor.setText(intent.getStringExtra("floor"));
        edtNumberOfBeds.setText(intent.getStringExtra("numberOfBeds"));
        edtImage.setText(intent.getStringExtra("image1"));




    }




    public void btnEdit(View view) {

        Log.d("start","start");
        String url = "http://10.0.2.2/hotel-system/editRoom.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                Intent intent = getIntent();
                intent.putExtra("roomNumber",roomNumber);
                intent.putExtra("floor",edtFloor.getText().toString());
                intent.putExtra("price",edtPrice.getText().toString());
                intent.putExtra("numberOfBeds",edtNumberOfBeds.getText().toString());
                intent.putExtra("typeOfBeds",spinner.getSelectedItem().toString());
                intent.putExtra("image1",edtImage.getText().toString());




                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(EditRoomActivity.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(intent);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(EditRoomActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> postParam = new HashMap<>();

                postParam.put("roomNumber",roomNumber);
                postParam.put("floor",edtFloor.getText().toString());
                postParam.put("price",edtPrice.getText().toString());
                postParam.put("numberOfBeds",edtNumberOfBeds.getText().toString());
                postParam.put("typeOfBeds",spinner.getSelectedItem().toString());
                postParam.put("image1",edtImage.getText().toString());


                return postParam;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

   
}