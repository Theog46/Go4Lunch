package com.example.go4lunch.Model;

public class User {

    private String uid;
    private String restaurantId;
    private String restaurantName;
    private String urlPicture;
    private String username;
    private String email;
    private Boolean like;
    private Boolean isEating;

    public User() {}

    public User(String uid, String restaurantId, String restaurantName,String urlPicture, String username, String email, Boolean like, Boolean isEating) {
        this.uid = uid;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.urlPicture = urlPicture;
        this.username = username;
        this.email = email;
        this.like = like;
        this.isEating = isEating;
    }

    public String getUid() { return uid; }
    public String getRestaurantId() { return restaurantId; }
    public String getRestaurantName() { return restaurantName; }
    public String getUrlPicture() { return urlPicture; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Boolean getLike() { return like; }
    public Boolean getIsEating() { return isEating; }
}
