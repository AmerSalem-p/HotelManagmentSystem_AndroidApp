package com.example.hotelsystem;

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
import model.user;

public class changeInfo extends AppCompatActivity {


    private EditText edtID;
    private EditText edtName;
    private EditText edtPhone;
    private Spinner edtSpinner;
    private EditText edtPassword;
    private Button edtButton;
    private Switch edtSwitch;
    private List<String> gender = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private user obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtSpinner = findViewById(R.id.edtGender);
        edtPassword = findViewById(R.id.edtPassword);
        edtButton = findViewById(R.id.edtButton);
        edtSwitch = findViewById(R.id.edtSwitch);
        readDataBase();
        edtID.setEnabled(false);


        edtSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (edtSwitch.isChecked()) {
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


    }


    public void btn_changeInfo(View view){



        String url = "http://10.0.2.2/hotel-system/updateInfo.php";

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                         new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Intent intent = getIntent();
                        intent.putExtra("Name",edtName.getText()+"");
                        intent.putExtra("Phone",edtPhone.getText()+"");
                        intent.putExtra("Gender",edtSpinner.getSelectedItem().toString());
                        intent.putExtra("Password",edtPassword.getText()+"");


                        if(obj.getPassword().compareTo(edtPassword.getText()+"")!=0){
                            Intent intent2 = new Intent(changeInfo.this,MainActivity.class);
                            startActivity(intent2);
                        }


                        finish();
                        startActivity(intent);

                        try{


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("userID", edtID.getText()+"");
                params.put("Name", edtName.getText()+"");
                params.put("Phone", edtPhone.getText()+"");
                params.put("Gender", edtSpinner.getSelectedItem().toString());
                params.put("Password", edtPassword.getText()+"");


                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void readDataBase(){

        gender.add("male");
        gender.add("female");
        gender.add("other");


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);

        Intent intent = getIntent();
        obj = (user)intent.getSerializableExtra("userObject");

        String url = "http://10.0.2.2/hotel-system/userLogin.php?id=" + obj.getUserID() + "&pass=" +obj.getPassword() ;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        try{

                            JSONObject obj = response.getJSONObject(0);
                            edtID.setText(obj.getInt("userID")+"");
                            edtName.setText(obj.getString("Name"));
                            edtPhone.setText(obj.getString("Phone"));
                            edtPassword.setText(obj.getString("Password"));
                            edtSpinner.setAdapter(adapter);
                            edtSpinner.setSelection(adapter.getPosition(obj.getString("Gender")));
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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