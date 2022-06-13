package com.example.go4lunch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Repository.NearbyRepository;
import com.example.go4lunch.Repository.UserLocationRepository;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.google.android.gms.location.LocationServices;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private volatile static ViewModelFactory instance;

    @NonNull
    private final NearbyRepository nearbyRepository;

    @NonNull
    private final UserLocationRepository userLocationRepository;



    public static ViewModelFactory getInstance() {
        if (instance == null) {
            synchronized (ViewModelFactory.class) {
                if (instance == null) {
                    Application app = MainApp.getApp();

                    instance = new ViewModelFactory(
                            new NearbyRepository(),
                            new UserLocationRepository(LocationServices.getFusedLocationProviderClient(app))

                    );
                }
            }
        }
        return instance;
    }


    private ViewModelFactory(
            @NonNull NearbyRepository nearbyRepository,
            @NonNull UserLocationRepository userLocationRepository

    ) {
        this.nearbyRepository = nearbyRepository;
        this.userLocationRepository = userLocationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantsListViewModel.class)) {
            return (T) new RestaurantsListViewModel(nearbyRepository, userLocationRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}