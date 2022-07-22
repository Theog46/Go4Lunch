package com.example.go4lunch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Repository.DetailRepository;
import com.example.go4lunch.Repository.FirestoreRepository;
import com.example.go4lunch.Repository.NearbyRepository;
import com.example.go4lunch.Repository.UserLocationRepository;
import com.example.go4lunch.ViewModel.DetailsRestaurantsViewModel;
import com.example.go4lunch.ViewModel.FirestoreViewModel;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.google.android.gms.location.LocationServices;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private volatile static ViewModelFactory instance;

    @NonNull
    private final NearbyRepository nearbyRepository;

    @NonNull
    private final UserLocationRepository userLocationRepository;

    @NonNull
    private final FirestoreRepository firestoreRepository;

    @NonNull
    private final DetailRepository detailRepository;


    public static ViewModelFactory getInstance() {
        if (instance == null) {
            synchronized (ViewModelFactory.class) {
                if (instance == null) {
                    Application app = MainApp.getApp();

                    instance = new ViewModelFactory(
                            new NearbyRepository(),
                            new UserLocationRepository(LocationServices.getFusedLocationProviderClient(app)),
                            new FirestoreRepository(),
                            new DetailRepository()

                    );
                }
            }
        }
        return instance;
    }


    private ViewModelFactory(
            @NonNull NearbyRepository nearbyRepository,
            @NonNull UserLocationRepository userLocationRepository,
            @NonNull FirestoreRepository firestoreRepository,
            @NonNull DetailRepository detailRepository
    ) {
        this.nearbyRepository = nearbyRepository;
        this.userLocationRepository = userLocationRepository;
        this.firestoreRepository = firestoreRepository;
        this.detailRepository =  detailRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantsListViewModel.class)) {
            return (T) new RestaurantsListViewModel(nearbyRepository, userLocationRepository, firestoreRepository);
        }
        else if (modelClass.isAssignableFrom(FirestoreViewModel.class)) {
            return (T) new FirestoreViewModel(firestoreRepository);
        }
        else if (modelClass.isAssignableFrom(DetailsRestaurantsViewModel.class)) {
            return (T) new DetailsRestaurantsViewModel(detailRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}