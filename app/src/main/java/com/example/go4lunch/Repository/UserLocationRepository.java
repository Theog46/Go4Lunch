package com.example.go4lunch.Repository;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Maps.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class UserLocationRepository {


    @NonNull
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();

    private LocationCallback callback;

    public UserLocationRepository(@NonNull FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }



    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void getLocation() {
        if (callback == null) {
            callback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location userLocation = new Location();
                    userLocation.setLat(locationResult.getLastLocation().getLatitude());
                    userLocation.setLng(locationResult.getLastLocation().getLongitude());
                    locationMutableLiveData.setValue(userLocation);
                }
            };
        }

        fusedLocationProviderClient.removeLocationUpdates(callback);
        fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(8000),
                callback,
                Looper.getMainLooper()
        );
    }


}
