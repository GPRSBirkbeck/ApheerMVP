package com.example.apheermvp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView message_owner_name, message_text;
    CircleImageView message_owner_image;
    //TODO uncomment the below if you want an onclick event
    OnPictureListener onPictureListener;

    //TODO uncomment the below if you want an onclick event
/*    //public FormerLocationsViewholder(@NonNull View itemView, OnRateListener onRateListener) {
            super(itemView);
    }*/

    public ConversationViewHolder(@NonNull View itemView, OnPictureListener onPictureListener) {
        super(itemView);
        //TODO uncomment the below if you want an onclick event
        this.onPictureListener = onPictureListener;
        message_owner_name = itemView.findViewById(R.id.message_owner_name);
        message_text = itemView.findViewById(R.id.message_text);
        message_owner_image = itemView.findViewById(R.id.message_owner_image);

        //TODO uncomment if you want an onClick
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onPictureListener.onFriendPictureClick(getAdapterPosition());
    }

    //TODO uncomment if you want an onClick
/*    @Override
    public void onClick(View v) {
        onRateListener.onRatesClick(getAdapterPosition());
    }*/
}
