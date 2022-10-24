package com.example.go4lunch.Model;

public class Message {
    private String uid;
    private String username;
    private String date;
    private String urlPicture;
    private String message;

    public Message() {}

    public Message(String uid, String username, String date, String urlPicture, String message) {
        this.uid = uid;
        this.username = username;
        this.date = date;
        this.urlPicture = urlPicture;
        this.message = message;
    }

    public String getUid() { return uid; }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlPicture() { return urlPicture; }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }
}
