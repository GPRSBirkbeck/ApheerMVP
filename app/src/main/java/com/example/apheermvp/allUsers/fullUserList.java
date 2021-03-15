package com.example.apheermvp.allUsers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.apheermvp.R;
import com.example.apheermvp.adapters.FriendListAdapter;
import com.example.apheermvp.adapters.LocationListAdapter;
import com.example.apheermvp.adapters.OnPictureListener;
import com.example.apheermvp.models.FormerLocation;
import com.example.apheermvp.models.Friend;
import com.example.apheermvp.ui.profile.ProfileViewModel;

import java.util.List;

public class fullUserList extends AppCompatActivity implements OnPictureListener {
    private RecyclerView mAllUsersRecyclerView;
    private FriendListAdapter mAllUserListAdapter;
    private FullUserListViewModel mFullUserListViewModel;
    private Friend mFriend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_user_list);
        mAllUsersRecyclerView = findViewById(R.id.all_users_recyclerView);
        mFullUserListViewModel =
                ViewModelProviders.of(this).get(FullUserListViewModel.class);


        mAllUserListAdapter = new FriendListAdapter(this);
        mAllUsersRecyclerView.setAdapter(mAllUserListAdapter);
        mAllUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subscribeObservers();


    }

    public void subscribeObservers(){
        mFullUserListViewModel.getAllUsers().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                if(friends!=null){
                    mAllUserListAdapter.setFriends(friends);
                }
            }
        });

    }

    @Override
    public void onFriendPictureClick(int position) {
        mFullUserListViewModel.addClickedFriend(getApplicationContext(), position);
    }

    @Override
    public void onFormerLocationClick(int position) {

    }

    public void toastMessage(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();

    }

}