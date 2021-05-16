package com.example.apheermvp.setLocation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apheermvp.R;
import com.example.apheermvp.landingPage.LandingActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetLocationAndNameActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //TAG
    private static final String TAG = "Adding location";

    //UI references
    private EditText mLocation, mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location_and_name);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAuth = FirebaseAuth.getInstance();

        //UI
        mLocation = (EditText) findViewById(R.id.location_edit_text);
        mUserName = (EditText) findViewById(R.id.set_user_name);

        if(mAuth.getCurrentUser().getDisplayName() == null){
            mUserName.setVisibility(View.VISIBLE);
        }


        //instantiate the DB
        db = FirebaseFirestore.getInstance();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pacific = new LatLng(8.7832, 124.5085);
        //mMap.addMarker(new MarkerOptions().position(pacific).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pacific, 1));

    }
    public void openLandingPage(){
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);

    }

    public void setLocationClick(View view){
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            String city = mLocation.getText().toString();
            if(mAuth.getCurrentUser().getDisplayName() == null){
                setUserName();
            }
            addDataToDatabase(city);
        }
    }

    public void setUserName(){
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String mName = mUserName.getText().toString();
        if(mName.equals("")){
            toastMessage("please enter a username");
        }
        else{
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(mName).build();
            currentUser.updateProfile(profileUpdates);
        }
    }

    public void addDataToDatabase(final String location){

        //TODO uncomment when I want the add functionality to be active again
/*
        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        final String userName = currentUser.getDisplayName();
        LocalDate currentdate = LocalDate.now();
        final double currentYear = currentdate.getYear();
        //TODO find a way of making the current_location work
        final GeoPoint current_location = new GeoPoint(51.509865, -0.118092);

        //reference to where the currrent_location is (or should be) stored
        final DocumentReference sfDocRef = db.collection("Locations").document(uid);

        final String finalUid = uid;
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshotOfCurrentLocation = transaction.get(sfDocRef);

                //check whether there is an existing current location
                if(snapshotOfCurrentLocation.getDouble("number_of_locations_counter") != null){
                    //get the number of locations previously visited and increment it
                    //get all the other values in current location so we can add them to a map and put that in previous locations.
                    double newNumberOfLocations = snapshotOfCurrentLocation.getDouble("number_of_locations_counter") + 1;

                    transaction.update(sfDocRef, "number_of_locations_counter", newNumberOfLocations);
                    String previousLocationString = snapshotOfCurrentLocation.getString("current_location");
                    double previous_time_arrived = snapshotOfCurrentLocation.getDouble("time_arrived");
                    GeoPoint previous_location = (GeoPoint) snapshotOfCurrentLocation.get("coordinates");
                    if(snapshotOfCurrentLocation.getDouble("number_of_locations_counter") == 0){
                        String previous_location_uid = "previous_location"  + uid + newNumberOfLocations;
                        final DocumentReference sfDocRef3 = db.collection("Locations").document(previous_location_uid);
                        Map<String, Object> previousSpotLevelOne = new HashMap<>();
                        previousSpotLevelOne.put("endDate", currentYear);
                        previousSpotLevelOne.put("startDate", previous_time_arrived);
                        previousSpotLevelOne.put("location", previousLocationString);
                        previousSpotLevelOne.put("geopoint", previous_location);
                        previousSpotLevelOne.put("userId", uid);
                        previousSpotLevelOne.put("locationId", "previous_location"  + uid+ newNumberOfLocations);

                        Map<String, Object> previousSpotLevelTwo = new HashMap<>();
                        Log.d(TAG, "apply: previousSpotLevelOne.hashCode()");

                        transaction.set(sfDocRef3, previousSpotLevelOne);
                    }
                    else{
                        Map<String, Object> previousSpot = new HashMap<>();
                        previousSpot.put("endDate", currentYear);
                        previousSpot.put("startDate", previous_time_arrived);
                        previousSpot.put("location", previousLocationString);
                        previousSpot.put("geopoint", previous_location);
                        previousSpot.put("userId", uid);
                        previousSpot.put("locationId", "previous_location"  + uid+ newNumberOfLocations);
                        //write the map to the previous_location document)


                        String previous_location_uid = "previous_location"  + uid+ newNumberOfLocations;
                        final DocumentReference sfDocRef3 = db.collection("Locations").document(previous_location_uid);
                        transaction.set(sfDocRef3, previousSpot);

                        //now update the current location
                        transaction.update(sfDocRef, "current_location", location);
                        transaction.update(sfDocRef, "time_arrived", currentYear);
                        transaction.update(sfDocRef, "coordinates", current_location);
                    }

                }
                else{
                    Map<String, Object> data = new HashMap<>();
                    data.put("number_of_locations_counter", 0.00);
                    data.put("current_location", location);
                    data.put("time_arrived", currentYear);
                    data.put("coordinates", current_location);
                    data.put("username", userName);
                    data.put("userId1", uid);
                    transaction.set(sfDocRef, data);

                }
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
                toastMessage("Location updated successfully");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });
*/

        openLandingPage();
    }

    public void toastMessage(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();

    }
}