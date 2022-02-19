package com.example.hotelsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import model.reservation;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {

    private  model.reservation[]reservation;
    private int []imageIds;
    private int temp;
    private Context mContext;
    private String customerName;
    private boolean []checkedIn;
    private boolean []checkedOut;


    public CheckAdapter(Context mContext, model.reservation[] reservation, int []imageIds, String customerName, boolean[] checkedIn,boolean []checkedOut) {
        this.reservation = reservation;
        this.imageIds=imageIds;
        this.mContext=mContext;
        this.customerName=customerName;
        this.checkedIn=checkedIn;
        this.checkedOut=checkedOut;
    }

    @NonNull
    @Override
    public CheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_check,
                parent,
                false);

        return new CheckAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(dr);
        TextView txt = (TextView)cardView.findViewById(R.id.resrvationId);
        txt.setText("Reaservation ID : " +reservation[position].getReservavtionId());
        TextView txt2 = (TextView)cardView.findViewById(R.id.reservationDate);
        txt2.setText("Reservation Date : " +reservation[position].getReserationDate());
        TextView txt3 = (TextView)cardView.findViewById(R.id.leaveDate);
        txt3.setText("Leave Date : " +reservation[position].getLeaveDate());
        TextView txt4 = (TextView)cardView.findViewById(R.id.moreInfo);
        txt4.setText("Customer ID : "+reservation[position].getCustomerId()+" / RoomNumber : "+reservation[position].getRoomNumber());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    temp = holder.getBindingAdapterPosition();
                    Intent intent = new Intent(mContext, CheckInfoActivity.class);
                    intent.putExtra("roomNumber", reservation[temp].getRoomNumber() + "");
                    intent.putExtra("leaveDate", reservation[temp].getLeaveDate().toString());
                    intent.putExtra("reservationDate", reservation[temp].getReserationDate().toString());
                    intent.putExtra("customerName", customerName);
                    intent.putExtra("checkIn", checkedIn[temp] + "");
                    intent.putExtra("checkOut", checkedOut[temp] + "");
                    intent.putExtra("image", imageIds[temp] + "");
                    intent.putExtra("reservationId",reservation[temp].getReservavtionId()+"");
                    mContext.startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return reservation.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
