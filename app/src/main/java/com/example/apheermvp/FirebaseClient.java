package com.example.apheermvp;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
/*

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
                            mFriends.postValue(mFriendList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
*/
/*
        db.collection("Relationships")
                .whereEqualTo("friend1", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final ArrayList<Friend> mFriendList1 = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //attempting an inner join
                                String otherUserId = document.getString("friend2");

                                db.collection("Locations")
                                        .whereEqualTo("userId1", otherUserId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    ArrayList<Friend> mFriendList2 = new ArrayList<>();

                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String friendName = document.getString("userName");
                                                        String friendLocation = document.getString("current_location");
                                                        String userId = document.getString("userId1");
                                                        Integer friendImage = R.drawable.macron_image;
                                                        Friend friend = new Friend(friendName, friendLocation, friendImage, userId);
                                                        mFriendList2.add(friend);
                                                    }
                                                    for (Friend friend: mFriendList2){
                                                        mFriendList1.add(friend);
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                            }
                            mFriends.postValue(mFriendList1);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/

/*        db.collection("Relationships")
                .whereEqualTo("friend2", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final ArrayList<Friend> mFriendList3 = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //attempting an inner join
                                String otherUserId = document.getString("friend1");

                                db.collection("Locations")
                                        .whereEqualTo("userId1", otherUserId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    ArrayList<Friend> mFriendList4 = new ArrayList<>();
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String friendName = document.getString("userName");
                                                        String friendLocation = document.getString("current_location");
                                                        String userId = document.getString("userId1");
                                                        Integer friendImage = R.drawable.macron_image;
                                                        Friend friend = new Friend(friendName, friendLocation, friendImage, userId);
                                                        mFriendList4.add(friend);
                                                    }
                                                    for (Friend friend: mFriendList4){
                                                        mFriendList3.add(friend);
                                                    }
                                                }

                                                else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                            }
                            mFriends.postValue(mFriendList3);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
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
                            mFriends.postValue(mFriendList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
/*        db.collection("Relationships")
                .whereEqualTo("friend1", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Friend> mFriendList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String otherUserId = document.getString("friend2");
                                String friendName = document.getString("friend2_name");
                                String friend1_location = document.getString("friend2_location");
                                Integer friendImage = R.drawable.macron_image;
                                Friend friend = new Friend(friendName,friend1_location, friendImage, otherUserId);
                                mFriendList.add(friend);
                            }
                            mFriends.postValue(mFriendList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
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

    public LiveData<List<Conversation>> getMessages() {
        //TODO fix this to load from DB once data is added

        db.collection("Conversations").document("chat1").collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Conversation> conversationArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String sender = document.getString("sender");
                                String text = document.getString("text");
                                Conversation conversation = new Conversation(sender,R.drawable.macron_image, text);
                                conversationArrayList.add(conversation);
                            }
                            mMessages.postValue(conversationArrayList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mMessages;


    }

    public void addMessageToConversation(String message, String documentRefence) {
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        Map<String, Object> docData = new HashMap<>();
        docData.put("participant1_name", "Gregory");
        docData.put("participant2_name", "Obama");
        docData.put("participant1_id", uid);
        docData.put("participant2_id", "E3p1CxfwcXgousx44tfjSnmekP92");
        docData.put("message_counter", 0.00);


        Map<String, Object> messageData = new HashMap<>();
        messageData.put("sender", uid);
        messageData.put("timeSent", new Timestamp(new Date()));
        messageData.put("text", message);
        docData.put("message1", messageData);
        db.collection("Conversations").document(documentRefence).collection("messages").add(messageData);
    }

}
