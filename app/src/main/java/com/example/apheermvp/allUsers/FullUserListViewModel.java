package com.example.apheermvp.allUsers;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apheermvp.adapters.OnPictureListener;
import com.example.apheermvp.models.Friend;
import com.example.apheermvp.repositories.ApheerRepository;

import java.util.List;

public class FullUserListViewModel extends ViewModel {
    private ApheerRepository mApheerRepository;

    public FullUserListViewModel() {
        mApheerRepository = ApheerRepository.getInstance();
    }


    public LiveData<List<Friend>> getAllUsers() {
        return mApheerRepository.getAllUsers();

    }

    public void addClickedFriend(Context applicationContext, int position) {
        mApheerRepository.addClickedFriend(applicationContext, position);
    }


}
