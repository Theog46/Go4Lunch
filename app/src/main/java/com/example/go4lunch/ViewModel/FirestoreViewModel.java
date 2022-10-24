package com.example.go4lunch.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Message;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.Repository.FirestoreRepository;

import java.util.List;

public class FirestoreViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    private static MutableLiveData<List<User>> allWorkmates = new MutableLiveData<>();
    private static MutableLiveData<List<Message>> allMessages = new MutableLiveData<>();

    public FirestoreViewModel(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    public void createUser(String uid, String restaurantId, String restaurantName, String urlPicture, String username, String email) {
        firestoreRepository.createUser(uid, restaurantId, restaurantName, urlPicture, username, email);
    }

    public MutableLiveData<List<User>> getAllWorkmates() {
        allWorkmates = firestoreRepository.getAllWorkmates();
        return allWorkmates;
    }

    public void chooseRestaurantToEat(String uid, String restaurantId, String restaurantName, Boolean isEating) {
        firestoreRepository.chooseRestaurantToEat(uid, restaurantId, restaurantName, isEating);
    }

    public void likeRestaurant(String uid) {
        firestoreRepository.likeRestaurant(uid);
    }

    public void removeLikeRestaurant(String uid) {
        firestoreRepository.removeLikeRestaurant(uid);
    }

    public LiveData<User> getYourLunch(String uid) {
        return Transformations.map(firestoreRepository.getYourLunch(uid), currentUser -> {
            if (currentUser == null) return null;
            else return currentUser;
        });
    }

    public MutableLiveData<List<Message>> getAllMessages() {
        allMessages = firestoreRepository.getAllMessages();
        return allMessages;
    }

    public void postMessage(String uid, String username, String urlPicture, String message, String date) {
        firestoreRepository.postMessage(uid, username, urlPicture, message, date);
    }
}
