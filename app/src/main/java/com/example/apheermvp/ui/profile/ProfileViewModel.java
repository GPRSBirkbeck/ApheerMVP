package com.example.apheermvp.ui.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileViewModel extends ViewModel {
    private FirebaseAuth mAuth;

    private MutableLiveData<String> mUserText;
    private MutableLiveData<String> mUserCurrentLocation;
    private FirebaseFirestore db;
    private static final String TAG = "ProfileViewModel";


    public ProfileViewModel() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String userName = currentUser.getDisplayName();
        final String[] userLocation = {currentUser.getDisplayName()};
        final String uid = currentUser.getUid();

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
                        final GeoPoint current_location = document.getGeoPoint("coordinates");
                        double lat = current_location.getLatitude();
                        double lng = current_location.getLongitude ();
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

    public LiveData<RevolutApiResponse> getFriends(){
        return mRatesRepository.getFriends();
    }

    public LiveData<RevolutApiResponse> getFormerLocation(){
        return mRatesRepository.getFormerLocation();
    }

}