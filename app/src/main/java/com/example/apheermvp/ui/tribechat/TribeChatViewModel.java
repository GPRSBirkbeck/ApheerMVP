package com.example.apheermvp.ui.tribechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apheermvp.models.Friend;
import com.example.apheermvp.repositories.ApheerRepository;

import java.util.List;

public class TribeChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ApheerRepository mApheerRepository;

    public TribeChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Invite your Friend");
        mApheerRepository = ApheerRepository.getInstance();

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Friend>> getFriends() {
       return mApheerRepository.getFriends();
    }

    public Friend getClickedFriend(int position) {
        Friend clickedFriend = mApheerRepository.getClickedFriend(position);
        return clickedFriend;
    }
}