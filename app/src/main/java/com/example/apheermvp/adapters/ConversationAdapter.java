package com.example.apheermvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apheermvp.R;
import com.example.apheermvp.models.Conversation;
import com.example.apheermvp.models.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
    final String uid = currentUser.getUid();

    private List<Conversation> mConversation = new ArrayList<>();
    //TODO if we want an onclick for friends, need to create an interface for the below onclick interface
    private OnPictureListener onPictureListener;

    public ConversationAdapter(OnPictureListener onPictureListener) {
        this.onPictureListener = onPictureListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_conversation_listitem, parent, false);
        return new ConversationViewHolder(view, onPictureListener);
        //uncomment and use below is you need an onclick
        //return new RatesViewholder(view, onLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //TODO change this to relate to the user's own picture
        StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
        //TODO sort this line out
        StorageReference profilePictures = mStorageReference.child("profilepictures/" + mConversation.get(position).getFriendName());
        //StorageReference profilePictures = mStorageReference.child("profilepictures/" + "E3p1CxfwcXgousx44tfjSnmekP92");

        if (mConversation.get(position).getFriendName().equals(uid)) {
            ((ConversationViewHolder)holder).message_owner_name.setText(currentUser.getDisplayName());
        }
        else{
            ((ConversationViewHolder)holder).message_owner_name.setText(mConversation.get(position).getFriendName());
        }
        ((ConversationViewHolder)holder).message_text.setText(mConversation.get(position).getMessageContent());

        RequestOptions requestOptions = new RequestOptions()
                //TODO replace the placeholder image with a feedme logo.
                .placeholder(R.drawable.jackie_chan);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(profilePictures)
                .into(((ConversationViewHolder)holder).message_owner_image);
        //.load(mRecipes.get(position).getImage())


        //((FormerLocationsViewholder)holder).city_image.setImageResource(mFormerLocations.get(position).getCityImage());
        //((FriendsViewholder)holder).friend_image.setImageResource(mFriends.get(position).getFriendImage());
    }

    @Override
    public int getItemCount() {
        if(mConversation != null){
            return mConversation.size();
        }
        return 0;
    }
    public void setMessages(List<Conversation> conversations){
        mConversation = conversations;
        notifyDataSetChanged();
    }
}
