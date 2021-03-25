package com.example.apheermvp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apheermvp.conversation.ConversationActivity;
import com.example.apheermvp.models.Conversation;
import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseClient {
    private static final String TAG = "FireBaseClient";
    private static FirebaseClient instance;
    private MutableLiveData<List<Friend>> mFriends;
    private MutableLiveData<List<Friend>> mAllUsers;
    private MutableLiveData<List<Conversation>> mMessages;
    private MutableLiveData<List<FormerLocation>> mFormerLocations;
    //firebase elements
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public static FirebaseClient getInstance(){
        if(instance == null){
            instance = new FirebaseClient();

        }
        return instance;
    }
    private FirebaseClient(){
        mFriends = new MutableLiveData<>();
        mFormerLocations = new MutableLiveData<>();
        mMessages = new MutableLiveData<>();
        mAllUsers = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();


    }
    public LiveData<List<Friend>> getAllUsers(){
        //final ArrayList<Friend> mFriendList = new ArrayList<>();
        db.collection("Locations")
                .whereGreaterThan("number_of_locations_counter",-1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Friend> mFriendList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String friendName = document.getString("userName");
                                String friendLocation = document.getString("current_location");
                                String userId = document.getString("userId1");
                                Integer friendImage = R.drawable.macron_image;
                                Friend friend = new Friend(friendName,friendLocation,friendImage, userId);
                                mFriendList.add(friend);
                            }
                            mAllUsers.postValue(mFriendList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //mAllUsers.postValue(mFriendList);
        return mAllUsers;
    }
    public LiveData<List<Friend>> getFriends() {
        final ArrayList<Friend> emptyFriendList = new ArrayList<>();
        mFriends.setValue(emptyFriendList);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        db.collection("Relationships")
                .whereEqualTo("friend2", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Friend> mFriendList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String otherUserId = document.getString("friend1");
                                String friendName = document.getString("friend1_name");
                                String friend1_location = document.getString("friend1_location");
                                Integer friendImage = R.drawable.macron_image;
                                Friend friend = new Friend(friendName,friend1_location, friendImage, otherUserId);
                                mFriendList.add(friend);
                            }
                            //mFriends.postValue(mFriendList);
                            for (Friend friend2 : mFriendList){
                                emptyFriendList.add(friend2);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("Relationships")
                .whereEqualTo("friend1", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Friend> mFriendList = new ArrayList<>();
                            //mFriendList.addAll(mFriends.);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String otherUserId = document.getString("friend2");
                                String friendName = document.getString("friend2_name");
                                String friend1_location = document.getString("friend2_location");
                                Integer friendImage = R.drawable.macron_image;
                                Friend friend = new Friend(friendName,friend1_location, friendImage, otherUserId);
                                mFriendList.add(friend);
                            }
                            for (Friend friend2 : mFriendList){
                                emptyFriendList.add(friend2);
                            }
                            //mFirstSetOfFriends.addAll(mFriendList);
                            mFriends.postValue(emptyFriendList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mFriends;
    }
    public LiveData<List<FormerLocation>> getFormerLocation(){
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        db.collection("Locations")
                .whereEqualTo("userId", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<FormerLocation> formerLocationList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cityName = document.getString("location");
                                String dates_in_former_city = "From "  + Math.round(document.getDouble("startDate")) + "to " + Math.round(document.getDouble("endDate"));
                                String locationId = document.getString("locationId");
                                Integer friendImage = R.drawable.london_photo;
                                FormerLocation formerLocation = new FormerLocation(cityName,dates_in_former_city,friendImage, locationId);
                                formerLocationList.add(formerLocation);
                            }
                            mFormerLocations.postValue(formerLocationList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mFormerLocations;
    }

    public void setFormerLocationPicture(int position) {
        FormerLocation clickedFormerLocation = mFormerLocations.getValue().get(position);
        //clickedFormerLocation.getCityImage();
        Log.d(TAG, "setFormerLocationPicture: clicked formerlocation" + clickedFormerLocation.getFormer_city_name());
    }

    public Friend getClickedFriend(int position) {
        Friend clickedFriend = mFriends.getValue().get(position);
        return clickedFriend;
    }

    public FormerLocation getClickedFormerLocation(int position) {
        FormerLocation clickedFormerLocation = mFormerLocations.getValue().get(position);
        return clickedFormerLocation;
    }

    public LiveData<List<Conversation>> getMessages(int position) {
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        String chatname;
        Friend clickedFriend = mFriends.getValue().get(position);
        String friendid = clickedFriend.getUserId();


        if((friendid.compareTo(uid))<0){
             chatname = "conversation_of"+uid+"AND"+friendid;
        }
        else{
            chatname ="conversation_of"+friendid+"AND"+uid;
        }

        db.collection("Conversations").document(chatname).collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Conversation> conversationArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String sender = document.getString("sender");
                                String text = document.getString("text");
                                String senderDisplayName = document.getString("senderDisplayName");
                                Timestamp timeSent = document.getTimestamp("timeSent");
                                Conversation conversation = new Conversation(senderDisplayName, sender, text, timeSent);
                                conversationArrayList.add(conversation);
                            }
                            sortMessagesByTimeStamp(conversationArrayList);
                            mMessages.postValue(conversationArrayList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mMessages;


    }

    public void addMessageToConversation(String message, int position, String userName) {
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        String chatname;
        Friend clickedFriend = mFriends.getValue().get(position);
        String friendid = clickedFriend.getUserId();
        if((friendid.compareTo(uid))<0){
            chatname = "conversation_of"+uid+"AND"+friendid;
        }
        else{
            chatname ="conversation_of"+friendid+"AND"+uid;
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("participant1_name", currentUser.getDisplayName());
        docData.put("participant2_name", clickedFriend.getFriendName());
        docData.put("participant1_id", uid);
        docData.put("participant2_id", clickedFriend.getUserId());


        Map<String, Object> messageData = new HashMap<>();
        messageData.put("senderDisplayName", userName);
        messageData.put("sender", uid);
        messageData.put("timeSent", new Timestamp(new Date()));
        messageData.put("text", message);
        docData.put("message1", messageData);
        db.collection("Conversations").document(chatname).collection("messages").add(messageData);
    }

    public ArrayList<Conversation> sortMessagesByTimeStamp(ArrayList<Conversation> conversationArrayList) {
        Collections.sort(conversationArrayList, messageTimeStampComparator);
        return conversationArrayList;
    }

    public static Comparator<Conversation> messageTimeStampComparator = new Comparator<Conversation>() {
        @Override
        public int compare(Conversation c1, Conversation c2) {
                return c1.getTimeSent().compareTo(c2.getTimeSent());
            }
        };

    public void addClickedFriend(Context context, int position) {
        Friend clickedFriend = mAllUsers.getValue().get(position);

        //check to see if this person is already a friend
        for (int i = 0; i < mFriends.getValue().size(); i++) {
            if(clickedFriend.getUserId().equals(mFriends.getValue().get(i).getUserId())){
                String username = mFriends.getValue().get(i).getFriendName();
                Toast.makeText(context, "you are already friends with " + username, Toast.LENGTH_SHORT).show();
                return;

            }
        }

        String referenceString;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        Map<String, Object> docData = new HashMap<>();
        docData.put("friend1", uid);
        docData.put("friend1_location", "Italia");
        docData.put("friend1_name", currentUser.getDisplayName());
        docData.put("friend2", clickedFriend.getUserId());
        docData.put("friend2_location", clickedFriend.getFriend_location());
        docData.put("friend2_name", clickedFriend.getFriendName());
        if((clickedFriend.getUserId().compareTo(uid))<0){
            referenceString = "conversation_of"+uid+"AND"+clickedFriend.getUserId();
        }
        else{
            referenceString = "conversation_of"+clickedFriend.getUserId()+"AND"+uid;
        }
        docData.put("conversationId", referenceString);
        db.collection("Relationships").document().set(docData);

        Map<String, Object> conversationData = new HashMap<>();
        conversationData.put("participant1_name", currentUser.getDisplayName());
        conversationData.put("participant2_name", clickedFriend.getFriendName());
        conversationData.put("participant1_id", uid);
        conversationData.put("participant2_id", clickedFriend.getUserId());
        conversationData.put("message_counter", 0.00);


        db.collection("Conversations").document(referenceString).set(conversationData);

        Toast.makeText(context, "Congratulations - you are now friends with " + clickedFriend.getFriendName(), Toast.LENGTH_SHORT).show();
    }
}
