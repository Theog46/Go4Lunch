package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Model.Maps.Result;
import com.example.go4lunch.Model.Maps.ResultsApiMaps;
import com.example.go4lunch.Service.RetrofitAPI;
import com.example.go4lunch.Service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyRepository {

    private static final String API_KEYWORD = "restaurant";

    private static final String radius = "1500";

    private static final MutableLiveData<List<Result>> nearbyResults = new MutableLiveData<>();

    public MutableLiveData<List<Result>> getNearbySearch(String userLocation) {
        RetrofitAPI api = RetrofitService.getClient().create(RetrofitAPI.class);
        if (userLocation != null) {
            Call<ResultsApiMaps> nearbyPlaces = api.getNearbySearch(
                    userLocation,
                    API_KEYWORD,
                    BuildConfig.MAPS_API_KEY,
                    radius
            );

            nearbyPlaces.enqueue(new Callback<ResultsApiMaps>() {

                @Override
                public void onResponse(Call<ResultsApiMaps> call, Response<ResultsApiMaps> response) {
                    if (response.isSuccessful()) {
                        Log.d("OKAY", "GET GOOD");
                        ResultsApiMaps bdy = response.body();
                        if (bdy != null) {

                            nearbyResults.setValue(bdy.getResults());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultsApiMaps> call, Throwable t) {
                    Log.d("ERREUR", "GETPLACE PROBLEME");
                }
            });
        }
        return nearbyResults;
    }


}
