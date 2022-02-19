package com.example.hotelsystem;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.MySingleton;
import model.room;
import model.user;



public class adapter2
        extends RecyclerView.Adapter<adapter2.ViewHolder>{

    private int[] reservationID;
    private int[] customerID;
    private int[]roomNumber ;
    private String[] reservationDate;
    private String[] leaveDate;
    private Context mContext;
    private int temp;
    public  adapter2(Context mContext, int []reservationID, int[] customerID, int[] roomNumber,String[]reservationDate,String[]leaveDate){
        this.mContext = mContext;
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.roomNumber = roomNumber;
        this.reservationDate = reservationDate;
        this.leaveDate = leaveDate;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rooms2,
                parent,
                false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView txt = (TextView) cardView.findViewById(R.id.reservationID);
        txt.setText("Reservation ID : " + reservationID[position]);

        TextView txt2 = (TextView) cardView.findViewById(R.id.customerID2);
        txt2.setText("Customer ID : " + customerID[position]);

        TextView txt3 = (TextView) cardView.findViewById(R.id.roomNumber2);
        txt3.setText("Room Number : " + roomNumber[position]);

        TextView txt4 = (TextView) cardView.findViewById(R.id.reservationDate);
        txt4.setText("Reservation Date : " + reservationDate[position].toString());

        TextView txt5 = (TextView) cardView.findViewById(R.id.leaveDate);
        txt5.setText("Leave Date : " + leaveDate[position].toString());


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.cardView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                temp = holder.getBindingAdapterPosition();

                String url = "http://10.0.2.2/hotel-system/deleteReservation.php";



                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Are you Sure you Want to cancel this reservation.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                StringRequest stringRequest = new StringRequest
                                        (Request.Method.POST, url, new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {

                                                try{
                                                    Intent intent = new Intent(mContext,customerFunctions.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                    mContext.startActivity(intent);
                                                } catch (Exception e) {
                                                    //  Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                //Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("reservationID", String.valueOf(reservationID[temp]));
                                        return params;
                                    }
                                };

                                MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }


        });

    }

    @Override
    public int getItemCount() {
        return reservationID.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
