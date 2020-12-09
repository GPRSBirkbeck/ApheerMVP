package com.example.apheermvp.ui.profile;

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
import com.google.firebase.auth.FirebaseAuth;

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
    private LocationListAdapter mLocationListAdapater;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

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

        initRecylcerViews();
        subscribeObservers();

        return root;
    }
    public void subscribeObservers(){
        mProfileViewModel.getFriends().observe(this, new androidx.lifecycle.Observer<RevolutApiResponse>() {
            @Override
            public void onChanged(RevolutApiResponse revolutApiResponse) {
                if(revolutApiResponse!=null){
                    //we are viewing livedata so that the data doesnt change if the user changes state (e.g. screen lock)
                    //we want the adapter below to be notified if changes are made to our livedata
                    ModelListCurrencyRate modelListCurrencyRate = new ModelListCurrencyRate(revolutApiResponse);
                    mFriendListAdapter.setRates(modelListCurrencyRate.getCurrencyRateList());
                    ModelListCurrencyRate modelListCurrencyRate = new ModelListCurrencyRate(revolutApiResponse);
                    mLocationListAdapater.setRates(modelListCurrencyRate.getCurrencyRateList());
                }
            }
        });
        mProfileViewModel.getFormerLocation().observe(this, new androidx.lifecycle.Observer<RevolutApiResponse>() {
            @Override
            public void onChanged(RevolutApiResponse revolutApiResponse) {
                if(revolutApiResponse!=null){
                    //we are viewing livedata so that the data doesnt change if the user changes state (e.g. screen lock)
                    //we want the adapter below to be notified if changes are made to our livedata
                    ModelListCurrencyRate modelListCurrencyRate = new ModelListCurrencyRate(revolutApiResponse);
                    mLocationListAdapater.setRates(modelListCurrencyRate.getCurrencyRateList());
                }
            }
        });
    }

    //set up recyclerViews
    private void initRecylcerViews(){
        mFriendListAdapter = new FriendListAdapter(this);
        mFriendsRecylcerView.setAdapter(mFriendListAdapter);
        mFriendsRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        mLocationListAdapater = new LocationListAdapter(this);
        mLocationsRecyclerView.setAdapter(mLocationListAdapater);
        mLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}