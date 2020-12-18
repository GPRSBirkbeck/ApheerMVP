package com.example.apheermvp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;

import de.hdodenhof.circleimageview.CircleImageView;
//TODO uncomment the below if you want an onclick event
//public class FormerLocationsViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
public class FormerLocationsViewholder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    TextView former_location_name, Dates_in_former_city;
    CircleImageView city_image;
    //TODO uncomment the below if you want an onclick event
    OnPictureListener onPictureListener;

    //TODO uncomment the below if you want an onclick event
/*    //public FormerLocationsViewholder(@NonNull View itemView, OnRateListener onRateListener) {
            super(itemView);
    }*/

    public FormerLocationsViewholder(@NonNull View itemView, OnPictureListener onPictureListener) {
        super(itemView);
        //TODO uncomment the below if you want an onclick event
        //this.onRateListener = onRateListener;
        this.onPictureListener = onPictureListener;
        former_location_name = itemView.findViewById(R.id.former_city_name);
        Dates_in_former_city = itemView.findViewById(R.id.dates_in_former_city);
        city_image = itemView.findViewById(R.id.former_city_image);

        //TODO uncomment if you want an onClick
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPictureListener.onFormerLocationClick(getAdapterPosition());
    }

    //TODO uncomment if you want an onClick
/*    @Override
    public void onClick(View v) {
        onRateListener.onRatesClick(getAdapterPosition());
    }*/
}
