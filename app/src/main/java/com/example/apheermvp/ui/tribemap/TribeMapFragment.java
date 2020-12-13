package com.example.apheermvp.ui.tribemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.apheermvp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TribeMapFragment extends Fragment {

    private TribeMapViewModel tribeMapViewModel;
    private SupportMapFragment mapFragment;
    //firebase vars
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "TribeMapFragment";
    private LatLng latLng;
    private GoogleMap map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        //instantiate the DB
        db = FirebaseFirestore.getInstance();

        tribeMapViewModel =
                ViewModelProviders.of(this).get(TribeMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tribe_map, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        tribeMapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //SOlution from: https://code.luasoftware.com/tutorials/android/supportmapfragment-in-fragment/
        if (mapFragment == null) {
            final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
            final String uid = currentUser.getUid();
            final String userName = currentUser.getDisplayName();
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if(latLng!=null){
                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                .title(userName));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                    else{
                        LatLng london = new LatLng(51.5074, 0.1278);
                        googleMap.addMarker(new MarkerOptions().position(london)
                                .title("London"));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(london));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 15));
                    }
                }

            });
            // R.id.map is a FrameLayout, not a Fragment
            getChildFragmentManager().beginTransaction().replace(R.id.mapTribe, mapFragment).commit();
            DocumentReference docRef = db.collection("Locations").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            final GeoPoint current_location = document.getGeoPoint("coordinates");
                            double lat = current_location.getLatitude();
                            double lng = current_location.getLongitude ();
                            latLng = new LatLng(lat, lng);

                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(final GoogleMap googleMap) {
                                    //BitmapDescriptor icon = getMarkerIconFromDrawable(R.drawable.jackie_chan);
                                    //BitmapDescriptor icon = bitmapDescriptorFromVector(getContext(), R.drawable.jackie_chan);

                                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                                            R.drawable.jackie_chan);
                                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(icon);
                                    if(latLng!=null){
                                        googleMap.addMarker(new MarkerOptions().position(latLng)
                                                .title(userName)
                                                .icon(bitmapDescriptor));

                                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                    }
                                    else{
                                        LatLng london = new LatLng(51.5074, 0.1278);
                                        googleMap.addMarker(new MarkerOptions().position(london)
                                                .title("London"));
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(london));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 15));
                                    }
                                    db.collection("Locations")
                                            .whereGreaterThan("number_of_locations_counter",-1)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            //BitmapDescriptor icon = getMarkerIconFromDrawable( R.drawable.jackie_chan);
                                                            Drawable drawable = getResources().getDrawable(R.drawable.jackie_chan);
                                                            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                                                                    R.drawable.jackie_chan);
                                                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(icon);

                                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                                            final GeoPoint current_location = document.getGeoPoint("coordinates");
                                                            double lat = current_location.getLatitude();
                                                            double lng = current_location.getLongitude ();
                                                            latLng = new LatLng(lat, lng);
                                                            googleMap.addMarker(new MarkerOptions().position(latLng)
                                                                    .title(document.getString("userName"))
                                                                    .icon(bitmapDescriptor));
                                                        }
                                                    } else {
                                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        return root;
    }

/*    private BitmapDescriptor getMarkerIconFromDrawable(int drawableInt) {
        Drawable drawable = getResources().getDrawable(drawableInt);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, 24, 24);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.map_marker);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth() + 0, vectorDrawable.getIntrinsicHeight() + 0);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }*/
}