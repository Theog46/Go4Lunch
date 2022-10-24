package com.example.go4lunch.ViewModel;

import com.example.go4lunch.Model.Maps.Photo;

import java.util.List;

public class DetailRestaurantItem {

    private String id;
    private String name;
    private String address;
    private List<Photo> photos;
    private String phone;
    private String website;
    private Float stars;
    private Boolean like;

    public DetailRestaurantItem(String id, String name, String address, List<Photo> photos, String phone, String website, Float stars, Boolean like) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.photos = photos;
        this.phone = phone;
        this.website = website;
        this.stars = stars;
        this.like = like;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public Float getStars() {
        return stars;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public List<Photo> getPhotos() { return photos; }

    public Boolean getLike() { return like; }

    public void setLike(Boolean like) {
        this.like = like;
    }
}
