package com.example.go4lunch.Service;

import com.example.go4lunch.Model.Maps.ResultsApiMaps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("nearbysearch/json")
    Call<ResultsApiMaps> getNearbySearch(
            @Query("location") String location,
            @Query("type") String restaurant,
            @Query("key") String type,
            @Query("radius") String radius);


}
