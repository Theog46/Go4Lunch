package com.example.go4lunch.Model.AutoComplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Predictions {

    @SerializedName("status")
    private final String status;

    @SerializedName("predictions")
    private final List<ResultsAutoComplete> predictions;

    public Predictions(String status, List<ResultsAutoComplete> predictions) {
        this.status = status;
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public List<ResultsAutoComplete> getPredictions() {
        return predictions;
    }
}
