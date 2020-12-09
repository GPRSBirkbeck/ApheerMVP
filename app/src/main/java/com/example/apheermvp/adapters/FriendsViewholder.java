package com.example.apheermvp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;

import de.hdodenhof.circleimageview.CircleImageView;

import de.hdodenhof.circleimageview.CircleImageView;
//TODO uncomment the below if you want an onclick event
//public class FormerLocationsViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
public class FriendsViewholder extends RecyclerView.ViewHolder  {
    TextView friend_name, friend_location;
    CircleImageView friend_image;
    //TODO uncomment the below if you want an onclick event
    //OnRateListener onRateListener;

    //TODO uncomment the below if you want an onclick event
/*    //public FormerLocationsViewholder(@NonNull View itemView, OnRateListener onRateListener) {
            super(itemView);
    }*/

    public FriendsViewholder(@NonNull View itemView) {
        super(itemView);
        //TODO uncomment the below if you want an onclick event
        //this.onRateListener = onRateListener;
        friend_name = itemView.findViewById(R.id.friend_name);
        friend_location = itemView.findViewById(R.id.friend_location);
        friend_image = itemView.findViewById(R.id.friend_image);

        //TODO uncomment if you want an onClick
        //itemView.setOnClickListener(this);
    }

    //TODO uncomment if you want an onClick
/*    @Override
    public void onClick(View v) {
        onRateListener.onRatesClick(getAdapterPosition());
    }*/
}