package com.example.go4lunch.Service;

import com.example.go4lunch.Model.Details.ResultsApiDetails;
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

    @GET("details/json")
    Call<ResultsApiDetails> getDetails(
            @Query("place_id") String place_id,
            @Query("fields") String fields,
            @Query("key") String key
    );
}
