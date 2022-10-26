package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Message;
import com.example.go4lunch.Model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection;
    private final CollectionReference messagesCollection;
    private MutableLiveData<List<User>> listMutableLiveData;
    private static List<User> data = new ArrayList<>();
    private MutableLiveData<List<User>> workmatesThatEat;
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private static ArrayList<User> workmatesList = new ArrayList<>();
    private MutableLiveData<List<Message>> messagesMutableLiveData;
    private static ArrayList<Message> messagesList = new ArrayList<>();


    public FirestoreRepository() {
        Log.i("REPO", "APPEL FIRESTOREREPO");
        usersCollection = db.collection("users");

        messagesCollection = db.collection("messages");
    }

    public MutableLiveData<List<Message>> getAllMessages() {
        messagesMutableLiveData = new MutableLiveData<>();
        messagesCollection.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener((queryDocumentSnapshots, error) -> {
            if (queryDocumentSnapshots == null) {
            } else {
                messagesList.clear();
                for (DocumentSnapshot message : queryDocumentSnapshots.getDocuments()) {
                    messagesList.add(message.toObject(Message.class));
                }
            }
        });
        messagesMutableLiveData.setValue(messagesList);
        return messagesMutableLiveData;
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

    public MutableLiveData<List<User>> getAllWorkmates() {
        workmatesThatEat = new MutableLiveData<>();
        usersCollection.addSnapshotListener((queryDocumentSnapshots, error) -> {
            if (queryDocumentSnapshots == null) {
            } else {
                List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
                workmatesList.clear();
                for (DocumentSnapshot user : queryDocumentSnapshots.getDocuments()) {
                    workmatesList.add(user.toObject(User.class));
                }
            }
        });
        workmatesThatEat.setValue(workmatesList);
        return workmatesThatEat;
    }

    public void createUser(String uid, String restaurantId, String restaurantName, String urlPicture, String username, String email) {
        usersCollection.document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.d("DOCUS", documentSnapshot.toString());

                        if (!documentSnapshot.exists()) {
                            User user = new User(uid, restaurantId, restaurantName, urlPicture, username, email, false, false);
                            usersCollection.document(uid).set(user);
                        }
                    }
                    else {
                        Log.d("REPO", "oups" + task.getException());
                    }
                });
    }

    public void chooseRestaurantToEat(String uid, String restaurantId, String restaurantName, Boolean isEating) {
        usersCollection.document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        usersCollection.document(uid).update("restaurantId" , restaurantId, "restaurantName", restaurantName, "isEating", isEating);

                    }
                });
    }

    public void yourLunch(String uid) {
        usersCollection.document(uid)
                .addSnapshotListener((value, error) -> {
                   currentUser.setValue(value.toObject(User.class));
                });
    }

    public MutableLiveData<User> getYourLunch(String uid) {
        yourLunch(uid);
        return currentUser;
    }

    public void likeRestaurant(String uid) {
        usersCollection.document(uid)
                .get()
                .addOnCompleteListener(task -> {
                  if (task.isSuccessful()) {
                      usersCollection.document(uid).update("like", true);
                  }
                });
    }

    public void removeLikeRestaurant(String uid) {
        usersCollection.document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        usersCollection.document(uid).update("like", false);
                    }
                });
    }


}
