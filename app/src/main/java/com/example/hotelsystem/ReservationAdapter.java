package com.example.hotelsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.MySingleton;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
   private ArrayList<String> customerName;
   private ArrayList<Integer> roomNumber;
   private ArrayList<Integer> reservationId;
   private ArrayList<String> checkedInDate;
   private ArrayList<String> checkedOutDate;
   private ArrayList<String> reservationDate;
   private ArrayList<String> leaveDate;
   private Context mContext;


    public ReservationAdapter(ArrayList<String> customerName, ArrayList<Integer> roomNumber, ArrayList<Integer> reservationId, ArrayList<String> checkedInDate, ArrayList<String> checkedOutDate, ArrayList<String> reservationDate, ArrayList<String> leaveDate, Context mContext) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.reservationId = reservationId;
        this.checkedInDate = checkedInDate;
        this.checkedOutDate = checkedOutDate;
        this.reservationDate = reservationDate;
        this.leaveDate = leaveDate;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reservation
                ,parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView txtReservationInfo=cardView.findViewById(R.id.reservationInfo);
        TextView txtReservationId=cardView.findViewById(R.id.reservationId);
        TextView txtReservationDate=cardView.findViewById(R.id.reservationDate);
        TextView txtLeaveDate=cardView.findViewById(R.id.leaveDate);
        TextView txtCheckedInDate=cardView.findViewById(R.id.checkedInDate);
        TextView txtCheckedOutDate=cardView.findViewById(R.id.checkedOutDate);

        txtReservationId.setText("Reservation Id : "+reservationId.get(position));
        txtCheckedInDate.setText("Checked In Date : "+checkedInDate.get(position));
        txtCheckedOutDate.setText("Checked Out Date : "+checkedOutDate.get(position));
        txtReservationInfo.setText("Customer_Name/Room_Number : "+customerName.get(position)+"/"+roomNumber.get(position));
        txtReservationDate.setText("Reservation Date : "+reservationDate.get(position));
        txtLeaveDate.setText("Leave Date : "+leaveDate.get(position));


        holder.cardView.findViewById(R.id.cancelReservation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setMessage("are you sure you want to cancel this reservation ");
                builder.setCancelable(true);

                builder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cancelReservation(position);
                            }
                        }
                );
                builder.setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void cancelReservation(int position)
    {
        String url="http://10.0.2.2/hotel-system/reservationReceptionist.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                customerName.remove(position);
                roomNumber.remove(position);
                reservationId.remove(position);
                checkedInDate.remove(position);
                checkedOutDate.remove(position);
                leaveDate.remove(position);
                reservationDate.remove(position);

                notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("taag",error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                Log.d("acc",String.valueOf(reservationId.get(position)));
                params.put("reservationID",String.valueOf(reservationId.get(position)));
                return params;
            }
        };

        MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    @Override
    public int getItemCount() {
        return reservationId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
       private CardView cardView;
       public ViewHolder(CardView cardView)
       {
           super(cardView);
           this.cardView=cardView;
       }
    }
}
