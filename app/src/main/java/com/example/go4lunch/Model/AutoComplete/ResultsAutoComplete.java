package com.example.go4lunch.Model.AutoComplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsAutoComplete {

    @SerializedName("place_id")
    private final String place_id;

    @SerializedName("types")
    private final List<String> types;

    @SerializedName("description")
    private final String description;

    public ResultsAutoComplete(String place_id, List<String> types, String description) {
        this.place_id = place_id;
        this.types = types;
        this.description = description;
    }

    public String getPlace_id() {
        return place_id;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }
}
