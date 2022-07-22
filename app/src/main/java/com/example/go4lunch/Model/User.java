package com.example.go4lunch.Model;

public class User {

    private String uid;
    private String urlPicture;
    private String username;
    private String email;

    public User() {}

    public User(String uid, String urlPicture, String username, String email) {
        this.uid = uid;
        this.urlPicture = urlPicture;
        this.username = username;
        this.email = email;
    }

    public String getUid() { return uid; }
    public String getUrlPicture() { return urlPicture; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
