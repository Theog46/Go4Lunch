package com.example.go4lunch.Model;

public class LikedRestaurants {

    private String userId;
    private String likedRestaurantName;
    private String likedRestaurantAddress;


    public LikedRestaurants() {}

    public LikedRestaurants(String userId, String likedRestaurantName, String likedRestaurantAddress) {
        this.userId = userId;
        this.likedRestaurantName = likedRestaurantName;
        this.likedRestaurantAddress = likedRestaurantAddress;

    }

    public String getUserId() { return userId; }
    public String getLikedRestaurantName() { return likedRestaurantName; }
    public String getLikedRestaurantAddress() { return likedRestaurantAddress; }

}
