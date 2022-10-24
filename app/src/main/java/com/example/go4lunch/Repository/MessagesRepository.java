package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Message;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MessagesRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference messagesCollection = db.collection("messages");
    private static final MutableLiveData<List<Message>> messagesResults = new MutableLiveData<>();
    private static ArrayList<Message> messageList = new ArrayList<>();

    public LiveData<List<Message>> getMessagesLiveData() {
        getAllMessages();
        return messagesResults;
    }

    public MutableLiveData<List<Message>> getAllMessages() {
        messagesCollection.addSnapshotListener((queryDocumentSnapshots, error) -> {
            if (queryDocumentSnapshots != null) {
                messageList.clear();
                for (DocumentSnapshot message : queryDocumentSnapshots.getDocuments()) {
                    messageList.add(message.toObject(Message.class));
                }
            }
        });
        messagesResults.setValue(messageList);
        return messagesResults;
    }

    public void postMessage(String uid, String username, String urlPicture, String message, String date) {
        messagesCollection.document("messages")
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       DocumentSnapshot documentSnapshot = task.getResult();
                       Log.d("MSGS", documentSnapshot.toString());

                       Message message1 = new Message(uid, username, date, urlPicture, message);
                       messagesCollection.document().set(message1);

                   }
                });
    }
}
