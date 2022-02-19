package com.example.hotelsystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.MySingleton;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private ArrayList<String> itemName;
    private ArrayList<String> customerName;
    private ArrayList<Integer> roomNumber;
    private ArrayList<Integer> orderNumber;
    private ArrayList<Integer> numberOfOrders;
    private ArrayList<Integer> price;
    private Context mContext;

    public ServiceAdapter(ArrayList<String> itemName, ArrayList<String> customerName, ArrayList<Integer> roomNumber, ArrayList<Integer> orderNumber, ArrayList<Integer> numberOfOrders, ArrayList<Integer> price, Context mContext) {
        this.itemName = itemName;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.orderNumber = orderNumber;
        this.numberOfOrders = numberOfOrders;
        this.price = price;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_services,
            parent,
            false);

        return new ServiceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView txtOrderNumber = cardView.findViewById(R.id.orderNumber);
        TextView txtItemName = cardView.findViewById(R.id.itemName);
        TextView txtCustomerName = cardView.findViewById(R.id.customerName);
        TextView txtRoomNumber = cardView.findViewById(R.id.roomNumber);
        TextView txtNumOfOrders = cardView.findViewById(R.id.numberOfOrders);
        TextView txtPrice = cardView.findViewById(R.id.price);

        txtOrderNumber.setText("order Id : "+orderNumber.get(position));
        txtItemName.setText("Item Name : "+itemName.get(position));
        txtCustomerName.setText("customer Name : "+customerName.get(position));
        txtRoomNumber.setText("Room Number : "+roomNumber.get(position));
        txtNumOfOrders.setText("Number Of Orders : "+numberOfOrders.get(position));
        txtPrice.setText("Price per one: "+price.get(position));

        cardView.findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAccept(holder);
            }
        });
        cardView.findViewById(R.id.reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReject(holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomNumber.size();
    }

    private void btnAccept(ViewHolder holder)
    {
        int position= holder.getBindingAdapterPosition();
        String url = "http://10.0.2.2/hotel-system/services.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    itemName.remove(position);
                    customerName.remove(position);
                    orderNumber.remove(position);
                    roomNumber.remove(position);
                    numberOfOrders.remove(position);
                    price.remove(position);

                    notifyDataSetChanged();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> postParam = new HashMap<>();

                postParam.put("isAccepted","1");
                postParam.put("orderNumber",orderNumber.get(position).toString());
                return postParam;
            }
        };
        MySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    private void btnReject(ViewHolder holder)
    {
        int position= holder.getBindingAdapterPosition();
        String url = "http://10.0.2.2/hotel-system/services.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemName.remove(position);
                customerName.remove(position);
                orderNumber.remove(position);
                roomNumber.remove(position);
                numberOfOrders.remove(position);
                price.remove(position);
                notifyDataSetChanged();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> postParam = new HashMap<>();

                postParam.put("isAccepted","2");
                postParam.put("orderNumber",orderNumber.get(position).toString());
                return postParam;
            }
        };
        MySingleton.getInstance(mContext).addToRequestQueue(request);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView=cardView;
        }
    }

}
