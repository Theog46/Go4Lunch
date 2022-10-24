package com.example.go4lunch.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Model.Details.Result;
import com.example.go4lunch.Model.Details.ResultsApiDetails;
import com.example.go4lunch.Service.RetrofitAPI;
import com.example.go4lunch.Service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {

    public DetailRepository() {}



    private static final String REQUEST_FIELDS = "place_id,name,geometry,formatted_address,photos,rating,formatted_phone_number,website";

    public static final MutableLiveData<Result> placeDetailResults = new MutableLiveData<Result>();

    public MutableLiveData<Result> getPlaceDetailResults(String placeId) {
        RetrofitAPI api = RetrofitService.getClient().create(RetrofitAPI.class);
        Call<ResultsApiDetails> placeDetails = api.getDetails(
                placeId,
                REQUEST_FIELDS,
                BuildConfig.MAPS_API_KEY
        );

        placeDetails.enqueue(new Callback<ResultsApiDetails>() {
            @Override
            public void onResponse(Call<ResultsApiDetails> call, Response<ResultsApiDetails> response) {
                if (response.isSuccessful()) {
                    ResultsApiDetails bdy = response.body();
                    if (bdy != null) {
                        placeDetailResults.setValue(bdy.getResult());

                    }
                }
            }

            @Override
            public void onFailure(Call<ResultsApiDetails> call, Throwable t) {

            }
        });
        return placeDetailResults;
    }

    public MutableLiveData<Result> getPlaceDetailResults() {

        return placeDetailResults;

    }


}
