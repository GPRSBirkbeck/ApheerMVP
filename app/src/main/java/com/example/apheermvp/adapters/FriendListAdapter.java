package com.example.apheermvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apheermvp.R;
import com.example.apheermvp.models.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        //TODO change this to relate to the user's own picture
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profilePictures = mStorageReference.child("profilepictures/" + uid);

        ((FriendsViewholder)holder).friend_name.setText(mFriends.get(position).getFriendName() + " ");
        ((FriendsViewholder)holder).friend_location.setText("in " + mFriends.get(position).getFriend_location());

        RequestOptions requestOptions = new RequestOptions()
                //TODO replace the placeholder image with a feedme logo.
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(profilePictures)
                .into(((FriendsViewholder)holder).friend_image);
        //.load(mRecipes.get(position).getImage())


        //((FormerLocationsViewholder)holder).city_image.setImageResource(mFormerLocations.get(position).getCityImage());
        //((FriendsViewholder)holder).friend_image.setImageResource(mFriends.get(position).getFriendImage());
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
