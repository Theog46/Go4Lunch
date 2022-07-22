package com.example.go4lunch.ViewModel;


import com.example.go4lunch.Model.Maps.Photo;

import java.util.List;

public class RestaurantItem {
    private String place_id;
    private String name;
    private Double lat;
    private Double lng;
    private String vicinity;
    private List<Photo> pictures;
    private Integer range;
    private Float rating;
    private boolean isOpen;



    public RestaurantItem(String place_id,String name, Double lat, Double lng, String vicinity, List<Photo> pictures, Integer range, Float rating, Boolean isOpen) {
        this.place_id = place_id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.vicinity = vicinity;
        this.pictures = pictures;
        this.range = range;
        this.rating = rating;
        this.isOpen = isOpen;


    }

    public String getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Boolean getOpeningHours() { return isOpen; }

    public String getVicinity() {
        return vicinity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPictures() {
        return pictures;
    }

    public Integer getRange() { return range; }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }





}
