package com.example.hotelsystem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class adapter4 extends RecyclerView.Adapter<adapter4.ViewHolder>{

    private String[] Name;
    private int[] customerID;
    private String[] gender;
    private String [] phone;
    private Context mContext;

    private int temp;

    public adapter4(Context mContext, String[] Name, int[] customerID, String[] gender, String[] phone){
        this.mContext = mContext;
        this.Name = Name;
        this.customerID = customerID;
        this.gender = gender;
        this.phone = phone;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customer,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;



        TextView txt = (TextView)cardView.findViewById(R.id.customerid);
        txt.setText("customer id  : " +customerID[position]);
        TextView txt2 = (TextView)cardView.findViewById(R.id.name);
        txt2.setText("customer name : " +Name[position]);
        TextView txt3 = (TextView)cardView.findViewById(R.id.phone);
        txt3.setText("customer phone : " +phone[position] );
        TextView txt4 = (TextView)cardView.findViewById(R.id.gender);
        txt4.setText("cutomer gender : " + gender[position]);
    }



    @Override
    public int getItemCount() {
        return phone.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
