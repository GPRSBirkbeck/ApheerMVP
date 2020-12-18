package com.example.apheermvp;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseClient {
    private static final String TAG = "FireBaseClient";
    private static FirebaseClient instance;
    private MutableLiveData<List<Friend>> mFriends;
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
        db = FirebaseFirestore.getInstance();

    }
    public LiveData<List<Friend>> getFriends(){

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
                                String dates_in_former_city = document.getDouble("startDate").toString();
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
}
