package com.example.apheermvp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apheermvp.R;
import com.example.apheermvp.Sign_In_Activity;
import com.example.apheermvp.adapters.FriendListAdapter;
import com.example.apheermvp.adapters.LocationListAdapter;
import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mProfileViewModel;
    private Button signOutButton;
    private FirebaseAuth mAuth;
    private RecyclerView mFriendsRecylcerView;
    private RecyclerView mLocationsRecyclerView;
    private TextView userNameTextView;
    private TextView your_current_location;

    //adapters for our ViewModels
    private FriendListAdapter mFriendListAdapter;
    private LocationListAdapter mLocationListAdapter;
    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        context = container.getContext();

        mProfileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userNameTextView = root.findViewById(R.id.you_apheer_in);
        your_current_location = root.findViewById(R.id.you_apheer_in);
        mFriendsRecylcerView = root.findViewById(R.id.friends_recycler_view);
        mLocationsRecyclerView = root.findViewById(R.id.locations_recycler_view);

        signOutButton = (Button) root.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), Sign_In_Activity.class);
                v.getContext().startActivity(intent);
                getActivity().finish();


            }


        });


        //link up username to DB username (from viewmodel)
        mProfileViewModel.getUserNameText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                userNameTextView.setText(s);
            }
        });


        //link up user location to DB user location (from viewmodel)
        mProfileViewModel.getUserLocation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                your_current_location.setText(s);
            }
        });

        initRecylcerViews(context);
        subscribeObservers();

        return root;
    }
    public void subscribeObservers(){
        mProfileViewModel.getFriends().observe(getViewLifecycleOwner(), new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                if(friends!=null){
                    mFriendListAdapter.setFriends(friends);
                }
            }
        });
        mProfileViewModel.getFormerLocation().observe(getViewLifecycleOwner(), new Observer<List<FormerLocation>>() {
            @Override
            public void onChanged(List<FormerLocation> formerLocations) {
                if(formerLocations!=null){
                    mLocationListAdapter.setFormerLocations(formerLocations);
                }

            }
        });
    }

    //set up recyclerViews
    private void initRecylcerViews(Context context){
        //TODO add "this" into the constructors for new FriendListAdapter
        // and new LocationListAdapter if you want onclick events
        mFriendListAdapter = new FriendListAdapter();
        mFriendsRecylcerView.setAdapter(mFriendListAdapter);
        mFriendsRecylcerView.setLayoutManager(new LinearLayoutManager(context));

        mLocationListAdapter = new LocationListAdapter();
        mLocationsRecyclerView.setAdapter(mLocationListAdapter);
        mLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}