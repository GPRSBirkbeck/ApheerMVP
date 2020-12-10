package com.example.apheermvp;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.FormerLocationsList;
import com.example.apheermvp.models.Friend;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

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

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference applicationsRef = rootRef.collection("Locations");
        DocumentReference applicationIdRef = applicationsRef.document("previous_location" + uid);

/*        applicationIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<FormerLocation> FormerLocation = document.toObject(FormerLocationsList.class).FormerLocation;
                        //Use the the list
                        mFormerLocations.postValue(FormerLocation);
                    }
                }

        });*/

/*        db.collection("Locations")
                .whereEqualTo(FieldPath.documentId(),"previous_location" + uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<FormerLocation> mFormerLocationList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String location = document.getString("location");
                                String startDate = document.getString("startDate");
                                Integer cityImage = R.drawable.london_photo;
                                FormerLocation formerLocation = new FormerLocation(location,startDate,cityImage);
                                mFormerLocationList.add(formerLocation);
                            }
                            FormerLocation formerLocation = new FormerLocation("Brussels","2020",R.drawable.london_photo);
                            mFormerLocationList.add(formerLocation);
                            mFormerLocations.postValue(mFormerLocationList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
        List<FormerLocation> mFormerLocationList = new ArrayList<>();
        ArrayList<FormerLocation> mFormerLocation = new ArrayList<>();
        int drawable_source =R.drawable.london_photo;
        FormerLocation formerLocation = new FormerLocation("Brussels","2020", drawable_source);
        FormerLocation formerLocation2 = new FormerLocation("Germany","2020", drawable_source);
        FormerLocation formerLocation3 = new FormerLocation("Italy","2020", drawable_source);
        mFormerLocation.add(formerLocation);
        mFormerLocation.add(formerLocation2);
        mFormerLocation.add(formerLocation3);
        mFormerLocation.add(formerLocation3);
        mFormerLocation.add(formerLocation3);
        mFormerLocation.add(formerLocation3);
        mFormerLocations.postValue(mFormerLocation);
        return mFormerLocations;
    }

}
