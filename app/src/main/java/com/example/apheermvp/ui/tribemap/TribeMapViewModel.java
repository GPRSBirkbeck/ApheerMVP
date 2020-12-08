package com.example.apheermvp.ui.tribemap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TribeMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TribeMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}