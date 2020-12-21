package com.example.apheermvp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apheermvp.models.Conversation;
import com.example.apheermvp.models.Friend;
import com.example.apheermvp.repositories.ApheerRepository;

import java.util.List;

public class ConversationViewModel extends ViewModel {
    private ApheerRepository mApheerRepository;

    public ConversationViewModel(){
        mApheerRepository = ApheerRepository.getInstance();

    }

    public LiveData<List<Conversation>> getMessages() {
        return mApheerRepository.getMessages();
    }
}
