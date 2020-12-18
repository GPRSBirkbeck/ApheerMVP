package com.example.apheermvp.ui.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.example.apheermvp.repositories.ApheerRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private FirebaseAuth mAuth;

    private MutableLiveData<String> mUserText;
    private MutableLiveData<String> mUserCurrentLocation;
    private FirebaseFirestore db;
    private static final String TAG = "ProfileViewModel";
    private ApheerRepository mApheerRepository;
    private String uid;



    public ProfileViewModel() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String userName = currentUser.getDisplayName();
        final String[] userLocation = {currentUser.getDisplayName()};
        uid = currentUser.getUid();
        mApheerRepository = ApheerRepository.getInstance();

        //instantiate the DB
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Locations").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userLocation[0] = document.getString("current_location");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        mUserText = new MutableLiveData<>();
        mUserText.setValue(userName + ", you Apheer in:");
        mUserCurrentLocation = new MutableLiveData<>();
        mUserCurrentLocation.setValue(userLocation[0]);
    }

    public LiveData<String> getUserNameText() {
        return mUserText;
    }

    public LiveData<String> getUserLocation() {
        return mUserText;
    }

    public LiveData<List<Friend>> getFriends(){
        return mApheerRepository.getFriends();
    }

    public LiveData<List<FormerLocation>> getFormerLocation(){
        return mApheerRepository.getFormerLocation();
    }

    public String getUid() {
        return uid;
    }

    public void setFormerLocationPicture(int position) {
        mApheerRepository.setFormerLocationPicture(position);
    }

    public Friend getClickedFriend(int position) {
        Friend clickedFriend = mApheerRepository.getClickedFriend(position);
        return clickedFriend;
    }

    public FormerLocation getClickedCity(int position) {
        FormerLocation clickedFormerLocation = mApheerRepository.getClickedFormerLocation(position);
        return clickedFormerLocation;
    }
}