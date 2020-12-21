package com.example.apheermvp.repositories;

import androidx.lifecycle.LiveData;

import com.example.apheermvp.FirebaseClient;
import com.example.apheermvp.models.Conversation;
import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;

import java.util.List;

public class ApheerRepository {
    private static ApheerRepository instance;
    private FirebaseClient mFireBaseClient;


    public static ApheerRepository getInstance(){
        if(instance == null){
            instance = new ApheerRepository();
        }
        return instance;
    }
    private ApheerRepository(){
        mFireBaseClient = FirebaseClient.getInstance();
    }

    public LiveData<List<Friend>> getFriends(){
        return mFireBaseClient.getFriends();
    }

    public LiveData<List<FormerLocation>> getFormerLocation(){ return mFireBaseClient.getFormerLocation(); }


    public void setFormerLocationPicture(int position) {
        mFireBaseClient.setFormerLocationPicture(position);
    }

    public Friend getClickedFriend(int position) {
        Friend clickedFriend = mFireBaseClient.getClickedFriend(position);
        return clickedFriend;
    }

    public FormerLocation getClickedFormerLocation(int position) {
        FormerLocation clickedFormerLocation = mFireBaseClient.getClickedFormerLocation(position);
        return clickedFormerLocation;
    }

    public LiveData<List<Conversation>> getMessages() {
        return mFireBaseClient.getMessages();
    }
}
