package com.example.apheermvp.conversation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apheermvp.models.Conversation;
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

        public void addMessageToConversation(String message, String documentRefence, String userName) {
            mApheerRepository.addMessageToConversation(message, documentRefence, userName);
        }
}
