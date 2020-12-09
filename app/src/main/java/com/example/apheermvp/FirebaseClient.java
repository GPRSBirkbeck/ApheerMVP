package com.example.apheermvp;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
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
    private FirebaseClient(){ mFriends = new MutableLiveData<>();
        mFormerLocations = new MutableLiveData<>();
    }
    public LiveData<List<Friend>> getFriends(){
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
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
                                Integer friendImage = R.drawable.macron_image;
                                Friend friend = new Friend(friendName,friendLocation,friendImage);
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
        db.collection("Locations").document("previous_location" + uid)
                .whereEqualTo("title", "previous_location" + uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<FormerLocation> mFormerLocationsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String former_city_name = document.getString("location");
                                String dates_in_former_city = document.getString("endDate");
                                Integer city_image =R.drawable.london_photo;
                                FormerLocation formerLocation = new FormerLocation(former_city_name,dates_in_former_city,city_image);
                                mFormerLocationsList.add(formerLocation);
                            }
                            mFormerLocations.postValue(mFormerLocationsList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return mFormerLocations;
    }

}
