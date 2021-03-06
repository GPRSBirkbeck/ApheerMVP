package com.example.apheermvp.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.apheermvp.clients.FirebaseClient;
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

    public LiveData<List<Friend>> getAllUsers(){
        return mFireBaseClient.getAllUsers();
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

    public LiveData<List<Conversation>> getMessages(int friendid) {
        return mFireBaseClient.getMessages(friendid);
    }

    public void addMessageToConversation(String message, int friendid, String userName) {
        mFireBaseClient.addMessageToConversation(message, friendid, userName);
    }

    public void addClickedFriend(Context applicationContext, int position) {
        mFireBaseClient.addClickedFriend(applicationContext, position);

    }
}
