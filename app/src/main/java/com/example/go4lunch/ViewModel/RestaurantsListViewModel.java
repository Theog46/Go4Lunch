package com.example.go4lunch.ViewModel;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.Model.Maps.Geometry;
import com.example.go4lunch.Model.Maps.Location;
import com.example.go4lunch.Model.Maps.OpeningHours;
import com.example.go4lunch.Model.Maps.Result;

import com.example.go4lunch.Model.User;
import com.example.go4lunch.Repository.DetailRepository;
import com.example.go4lunch.Repository.FirestoreRepository;
import com.example.go4lunch.Repository.NearbyRepository;
import com.example.go4lunch.Repository.UserLocationRepository;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListViewModel extends ViewModel {

    NearbyRepository nearbyRepository;
    UserLocationRepository userLocationRepository;
    FirestoreRepository firestoreRepository;
    DetailRepository detailRepository;
    private Location userLocation;

    @SuppressLint("MissingPermission")
    public RestaurantsListViewModel(
            @NonNull NearbyRepository nearbyRepository,
            @NonNull UserLocationRepository userLocationRepository,
            @NonNull FirestoreRepository firestoreRepository,
            @NonNull DetailRepository detailRepository) {
        this.nearbyRepository = nearbyRepository;
        this.userLocationRepository = userLocationRepository;
        this.firestoreRepository = firestoreRepository;
        this.detailRepository = detailRepository;
        getLocation();
    }




    public static MutableLiveData<List<Result>> lRestaurantMutableLiveData = new MutableLiveData<>();

    public static MutableLiveData<ArrayList<com.example.go4lunch.Model.Details.Result>> lRestaurantResult = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<ArrayList<RestaurantItem>> lRestaurantItem = new MutableLiveData<>();

    public static MutableLiveData<com.example.go4lunch.Model.Details.Result> dRestaurant = new MutableLiveData<>();

    @SuppressLint("MissingPermission")
    public void getLocation() {
        if (false) {
            userLocationRepository.stopRequest();
        } else {
            userLocationRepository.getLocation();
        }
    }


    public LiveData<List<RestaurantItem>> mapDataToViewState
            (LiveData<List<Result>> lRestaurantMutableLiveData, List<User> workmates) {
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
                        isRestaurantOpen(result.getOpeningHours()),
                        getWorkmates(result.getPlaceId(), workmates)
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
        List<User> workmates = firestoreRepository.getAllWorkmates().getValue();
        return mapDataToViewState(lRestaurantMutableLiveData, workmates);
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

    @SuppressLint("MissingPermission")
    public Double getRange(Location restaurantLocation) {

        double distance;
        Location rLoc = new Location(restaurantLocation.getLat(), restaurantLocation.getLng());

        Location userLc = userLocationRepository.locationLiveData().getValue();


        distance = Location.computeDistance(userLc, rLoc);
        return distance;
    }

    private Integer getWorkmates(String placeId, List<User> workmates) {
        int workmatesNumber = 0;
        for (User user : workmates) {
            if (user.getRestaurantId() != null && user.getRestaurantId().equals(placeId)) {
                workmatesNumber++;
            }
        }
        return workmatesNumber;
    }

    public void callPlacesForAutoComplete(String placeId) {
        dRestaurant = detailRepository.getPlaceDetailResults(placeId);
    }

    public Location getLocationReinit() {
        return userLocationRepository.locationLiveData().getValue();
    }

    public LiveData<ArrayList<Result>> getDetailRestaurant() {
        detailRepository.getPlaceDetailResults();
        return mapData(dRestaurant);
    }

    private LiveData<ArrayList<Result>> mapData(LiveData<com.example.go4lunch.Model.Details.Result> dRestaurant) {
        return Transformations.map(dRestaurant, detailRestaurant -> {
            ArrayList<Result> lRestauDetail = new ArrayList<>();
            Result result = new Result();
            Geometry geometry = new Geometry();
            geometry.setLocation(detailRestaurant.getGeometry().getLocation());
            result.setPlaceId(detailRestaurant.getPlaceId());
            result.setName(detailRestaurant.getName());
            result.setVicinity(detailRestaurant.getVicinity());
            result.setRating(detailRestaurant.getRating());
            result.setPhotos(detailRestaurant.getPhotos());
            result.setOpeningHours(detailRestaurant.getOpeningHours());
            result.setGeometry(geometry);
            lRestauDetail.add(result);
            lRestaurantMutableLiveData.setValue(lRestauDetail);
            return lRestauDetail;
        });
    }

}
