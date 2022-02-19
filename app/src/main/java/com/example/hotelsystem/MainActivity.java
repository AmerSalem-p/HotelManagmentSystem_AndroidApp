package com.example.hotelsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import model.user;

public class MainActivity extends AppCompatActivity {

    private static final String NAME = "NAME";
    private static final String PASS = "PASS";
    private static final String FLAG = "FLAG";
    private SharedPreferences pref;
    private SharedPreferences.Editor edt;
    private EditText userid;
    private EditText userpass;
    private TextView loginInfo;
    private CheckBox box;
    TextView txt;
    TextView txt2;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid = findViewById(R.id.userID);
        userpass = findViewById(R.id.userPass);
        loginInfo = findViewById(R.id.loginInfo);
        box = findViewById(R.id.checkbox);
        setUpSharedPreference();
        checkPreference();

        txt = findViewById(R.id.regestration);
        txt2 = findViewById(R.id.getweather);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, regestration.class);
                startActivity(in);
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, weather.class);
                startActivity(intent);
            }
        });
    }

    public void btn_sign_in(View view) {



        if(userid.length()==0 || userpass.length()==0){
            loginInfo.setText("please fill all fields");
        }


        String name = userid.getText().toString();
        String password = userpass.getText().toString();

        if(box.isChecked()){

            edt.putString(NAME,name);
            edt.putString(PASS,password);
            edt.putBoolean(FLAG,true);
            edt.commit();

        }



            String url = "http://10.0.2.2/hotel-system/userLogin.php?id=" + userid.getText() + "&pass=" + userpass.getText();
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                    (Request.Method.GET, url,
                            null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {


                    try{

                        JSONObject obj = response.getJSONObject(0);
                        user userObject = new user();
                        userObject.setUserID(obj.getInt("userID"));
                        userObject.setName(obj.getString(("Name")));
                        userObject.setGender(obj.getString("Gender"));
                        userObject.setPhone(obj.getString("Phone"));
                        userObject.setPassword(obj.getString("Password"));
                        userObject.setIsReceptionist(obj.getInt("isReceptionist"));


                        Intent intent;
                        if(userObject.getIsReceptionist()==0){
                            intent = new Intent(MainActivity.this, customerFunctions.class);
                            intent.putExtra("userObject",userObject);
                        }

                        else{
                            intent = new Intent(MainActivity.this, receptionFunctions.class);
                            intent.putExtra("userObject",userObject);
                        }
                        startActivity(intent);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    loginInfo.setText("Wrong userID or Password");
                }
            });

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }


    private void setUpSharedPreference(){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        edt = pref.edit();
    }

    private void checkPreference(){

        boolean flag = pref.getBoolean(FLAG,false);

        if(flag){
            String name = pref.getString(NAME,"");
            String password = pref.getString(PASS,"");
            userid.setText(name);
            userpass.setText(password);
            box.setChecked(true);
        }

    }








}