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



public class adapter3
        extends RecyclerView.Adapter<adapter3.ViewHolder>{

    private int[] itemNumber;
    private int[] orderNumber;
    private int[] price;
    private int[] numberOfOrders;
    private int[] status;

    private Context mContext;
    private int temp;
    public  adapter3(Context mContext, int []itemNumber, int[] orderNumber, int[] price,int[]numberOfOrders,int[] status){
        this.mContext = mContext;
        this.itemNumber = itemNumber;
        this.orderNumber = orderNumber;
        this.price = price;
        this.numberOfOrders = numberOfOrders;
        this.status = status;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_orders,
                parent,
                false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView txt = (TextView) cardView.findViewById(R.id.orderNumber);
        txt.setText("Order Number : " + orderNumber[position]);

        TextView txt2 = (TextView) cardView.findViewById(R.id.itemNumber);
        txt2.setText("Item Number : " + itemNumber[position]);

        TextView txt3 = (TextView) cardView.findViewById(R.id.price);
        txt3.setText("price : " + price[position]);

        TextView txt4 = (TextView) cardView.findViewById(R.id.numberOfOrders);
        txt4.setText("Number of orders : " + numberOfOrders[position]);

        TextView txt5 = (TextView) cardView.findViewById(R.id.status);

        if(status[position] == 0) {
            txt5.setText("Status : Order is sent");
        }
        else if(status[position]==1){
            txt5.setText("Status : your order has been accepted");

        }
        else{
            txt5.setText("Status : order denied");
        }



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.cardView.findViewById(R.id.cancelOrder).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                temp = holder.getBindingAdapterPosition();

                String url = "http://10.0.2.2/hotel-system/deleteOrder.php";


                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Are you Sure you Want to cancel this order");
                builder1.setCancelable(true);

                if (status[temp] == 1) {
                    builder1.setMessage("Cant delete order food is ready");

                    builder1.setPositiveButton(
                            "Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });
                } else {
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    StringRequest stringRequest = new StringRequest
                                            (Request.Method.POST, url, new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        Intent intent = new Intent(mContext, customerFunctions.class);
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
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("orderNumber", String.valueOf(orderNumber[temp]));
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
                }
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return orderNumber.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
