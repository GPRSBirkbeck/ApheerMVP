package com.example.apheermvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;
import com.example.apheermvp.models.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Friend> mFriends = new ArrayList<>();
    //TODO if we want an onclick for friends, need to create an interface for the below onclick interface
/*    //private OnLocationListener onLocationListener;

    public LocationListAdapter(OnLocationListener onRateListener) {
        this.onLocationListener = onRateListener;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friends_listitem, parent, false);
        return new FriendsViewholder(view);
        //uncomment and use below is you need an onclick
        //return new RatesViewholder(view, onLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FriendsViewholder)holder).friend_name.setText(mFriends.get(position).getFriendName() + " ");
        ((FriendsViewholder)holder).friend_location.setText("in " + mFriends.get(position).getFriend_location());
        ((FriendsViewholder)holder).friend_image.setImageResource(mFriends.get(position).getFriendImage());
    }

    @Override
    public int getItemCount() {
        if(mFriends != null){
            return mFriends.size();
        }
        return 0;
    }
    public void setFriends(List<Friend> friends){
        mFriends = friends;
        notifyDataSetChanged();
    }
}
