package com.example.go4lunch.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Message;
import com.example.go4lunch.Repository.MessagesRepository;

import java.util.ArrayList;
import java.util.List;

public class MessagesViewModel extends ViewModel {

    private final MessagesRepository messagesRepository;


    public MessagesViewModel(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public LiveData<List<Message>> getAllMessages() {
        return Transformations.map(messagesRepository.getMessagesLiveData(), message -> {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            for (Message msg : message) {
                Message m = new Message(
                        msg.getUid(),
                        msg.getUsername(),
                        msg.getDate(),
                        msg.getUrlPicture(),
                        msg.getMessage()
                );
                messageArrayList.add(m);
            }
            return messageArrayList;
        });
    }


    public void postMessage(String uid, String username, String urlPicture, String message, String date) {
        messagesRepository.postMessage(uid, username, urlPicture, message, date);
    }
}
