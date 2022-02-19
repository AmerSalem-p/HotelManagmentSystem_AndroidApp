package com.example.hotelsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import model.room;
import model.user;


public class adapter
        extends RecyclerView.Adapter<adapter.ViewHolder>{

    private String[] numOfBeds;
    private int[] imageIds;
    private String[] type;
    private int[] price;
    private room[] rooms;
    private  room roomInfo = new room();
    private Context mContext;
    private user obj;
    private int temp;

    public  adapter(Context mContext, String[] numOfBeds, int[] imageIds, String[] type, int []price, room[] rooms, user obj){
        this.mContext = mContext;
        this.numOfBeds = numOfBeds;
        this.imageIds = imageIds;
        this.type = type;
        this.price = price;
        this.rooms = rooms;
        this.obj = obj;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rooms,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(dr);
        TextView txt = (TextView)cardView.findViewById(R.id.numOfBeds);
        txt.setText("Number of beds : " +numOfBeds[position]);
        TextView txt2 = (TextView)cardView.findViewById(R.id.type);
        txt2.setText("Type of beds : " +type[position]);
        TextView txt3 = (TextView)cardView.findViewById(R.id.price);
        txt3.setText("Price / Night : " +price[position] + "$");
        TextView txt4 = (TextView)cardView.findViewById(R.id.moreInfo);
        txt4.setText("Click for more Information about the room...");

        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                temp = holder.getBindingAdapterPosition();
                Intent intent = new Intent(mContext,moreInfo.class);
                intent.putExtra("roomNumber",rooms[temp].getRoomNumber());
                intent.putExtra("floor",rooms[temp].getFloor());
                intent.putExtra("numberOfBeds",rooms[temp].getNumberOfBeds());
                intent.putExtra("typeOfBeds",rooms[temp].getTypeOfBeds());
                intent.putExtra("price",rooms[temp].getPrice());
                intent.putExtra("size",rooms[temp].getSize());
                intent.putExtra("image1",rooms[temp].getImage1());
                intent.putExtra("userObject",obj);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numOfBeds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
