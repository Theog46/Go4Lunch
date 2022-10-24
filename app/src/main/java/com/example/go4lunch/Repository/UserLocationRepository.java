package com.example.go4lunch.Repository;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Model.Maps.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class UserLocationRepository {

    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();

    private LocationCallback locationCallback;

    private final FusedLocationProviderClient fusedLocationProviderClient;

    public UserLocationRepository(FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    public LiveData<Location> locationLiveData() {
        return locationMutableLiveData;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void getLocation() {
        if (locationCallback == null) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location location = new Location();
                    location.setLat(locationResult.getLastLocation().getLatitude());
                    location.setLng(locationResult.getLastLocation().getLongitude());
                    locationMutableLiveData.setValue(location);
                }
            };
        }
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                locationCallback,
                Looper.getMainLooper()
        );
    }
    public void stopRequest() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }
}
