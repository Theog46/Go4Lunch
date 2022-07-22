package com.example.go4lunch.ViewModel;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Maps.Location;
import com.example.go4lunch.Model.Maps.OpeningHours;
import com.example.go4lunch.Model.Maps.Result;
import com.example.go4lunch.Repository.FirestoreRepository;
import com.example.go4lunch.Repository.NearbyRepository;
import com.example.go4lunch.Repository.UserLocationRepository;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListViewModel extends ViewModel {

    NearbyRepository nearbyRepository;
    UserLocationRepository userLocationRepository;
    private Location location;


    public RestaurantsListViewModel(
            @NonNull NearbyRepository nearbyRepository,
            @NonNull UserLocationRepository userLocationRepository,
            @NonNull FirestoreRepository firestoreRepository) {
        this.nearbyRepository = nearbyRepository;
        this.userLocationRepository = userLocationRepository;

        getLocation();
    }

    private MutableLiveData<List<Result>> lRestaurantMutableLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<ArrayList<RestaurantItem>> lRestaurantItem = new MutableLiveData<>();

    @SuppressLint("MissingPermission")
    public void getLocation() {
        userLocationRepository.getLocation();
    }


    public LiveData<List<RestaurantItem>> mapDataToViewState
            (LiveData<List<Result>> lRestaurantMutableLiveData) {
        return Transformations.map(lRestaurantMutableLiveData, restaurant -> {
            ArrayList<RestaurantItem> restaurantStateItems = new ArrayList<>();

            for (Result result : restaurant) {
                restaurantStateItems.add(new RestaurantItem(
                        result.getPlaceId(),
                        result.getName(),
                        result.getGeometry().getLocation().getLat(),
                        result.getGeometry().getLocation().getLng(),
                        result.getVicinity(),
                        result.getPhotos(),
                        (int) Math.round(getRange(result.getGeometry().getLocation())),
                        (float) result.getRating(),
                        isRestaurantOpen(result.getOpeningHours())

                ));
            }

            lRestaurantItem.setValue(restaurantStateItems);
            return restaurantStateItems;

        });
    }

    public void initList(String userLocation) {
        lRestaurantMutableLiveData = nearbyRepository.getNearbySearch(userLocation);

    }

    public LiveData<List<RestaurantItem>> getRestaurants() {

        return mapDataToViewState(lRestaurantMutableLiveData);
    }

    private Boolean isRestaurantOpen(OpeningHours hours) {
        Boolean defineState;
        if (hours == null) {
            defineState = false;
        } else {
           defineState = hours.isOpenNow();
        }
        return defineState;
    }

    public Double getRange(Location restaurantLocation) {

        double distance;
        double lat;
        double lng;
        Location rLoc = new Location(restaurantLocation.getLat(), restaurantLocation.getLng());
        String str_user_lat = "37.4219983";
        String str_user_lng = "-122.084";

        lat = Double.parseDouble(str_user_lat);
        lng = Double.parseDouble(str_user_lng);

        Location userLoc = new Location(lat, lng);
        distance = Location.computeDistance(userLoc, rLoc);
        return distance;
    }



}
