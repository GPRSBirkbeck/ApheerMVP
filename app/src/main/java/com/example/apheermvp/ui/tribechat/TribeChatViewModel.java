package com.example.apheermvp.ui.tribechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TribeChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TribeChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Invite your Friend");
    }

    public LiveData<String> getText() {
        return mText;
    }
}