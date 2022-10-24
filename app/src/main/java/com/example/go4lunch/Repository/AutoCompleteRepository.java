package com.example.go4lunch.Repository;

import static com.example.go4lunch.fragments.MapsFragment.userLocation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Model.AutoComplete.Predictions;
import com.example.go4lunch.Model.AutoComplete.ResultsAutoComplete;
import com.example.go4lunch.Service.RetrofitAPI;
import com.example.go4lunch.Service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoCompleteRepository {

    private static final String TYPES = "establishment";

    private static final Integer RADIUS = 1500;

    private static final MutableLiveData<List<ResultsAutoComplete>> autoCompleteResults = new MutableLiveData<>();

    public void getAutoCompleteResults(String input) {
        RetrofitAPI api = RetrofitService.getClient().create(RetrofitAPI.class);
        Call<Predictions> predictionsCall = api.getAutoComplete(
                input,
                userLocation,
                TYPES,
                RADIUS,
                "",
                BuildConfig.MAPS_API_KEY
        );
        predictionsCall.enqueue(new Callback<Predictions>() {
            @Override
            public void onResponse(Call<Predictions> call, Response<Predictions> response) {
                if (response.isSuccessful()) {
                    Predictions predictions = response.body();
                    if (predictions != null) {
                        autoCompleteResults.setValue(predictions.getPredictions());
                    }
                }
            }

            @Override
            public void onFailure(Call<Predictions> call, Throwable t) {
                Log.d("AUTOCOMPLETE", "ERROR REPOSITORY");
            }
        });
    }
    public MutableLiveData<List<ResultsAutoComplete>> getAutoCompleteMutableLiveData() {
        return autoCompleteResults;
    }
}
