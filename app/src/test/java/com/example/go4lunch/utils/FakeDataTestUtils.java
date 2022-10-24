package com.example.go4lunch.utils;

import com.example.go4lunch.Model.Maps.Location;
import com.example.go4lunch.Model.Maps.Photo;
import com.example.go4lunch.Model.Maps.Result;
import com.example.go4lunch.Model.Message;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.ViewModel.RestaurantItem;

import java.util.ArrayList;
import java.util.List;

public class FakeDataTestUtils {



    public static List<User> createFakeUsersList() {
        List<User> fakeUsers = new ArrayList<>();

        User user0 = new User(
                "User 0 ID",
                "User 0 RESTAURANT_ID",
                "User 0 RESTAURANT_NAME",
                "User 0 URL_PICTURE",
                "User 0 USERNAME",
                "User 0 EMAIL",
                false,
                false
        );

        fakeUsers.add(user0);

        User user1 = new User(
                "User 1 ID",
                "User 1 RESTAURANT_ID",
                "User 1 RESTAURANT_NAME",
                "User 1 URL_PICTURE",
                "User 1 USERNAME",
                "User 1 EMAIL",
                true,
                true
        );

        fakeUsers.add(user1);

        return fakeUsers;
    }

    public static List<Message> createFakeMessages() {
        List<Message> messages = new ArrayList<>();

        Message message = new Message(
                "0",
                "Arthur",
                "DATE",
                "URL",
                "CONTENT_0"
        );

        messages.add(message);

        Message message1 = new Message(
                "1",
                "Jacques",
                "DATE",
                "URL",
                "CONTENT_1"
        );


        messages.add(message1);

        Message message2 = new Message(
                "2",
                "Jeanne",
                "DATE",
                "URL",
                "CONTENT_2"
        );


        messages.add(message2);

        return messages;
    }

    public static List<RestaurantItem> createFakeRestaurant() {
        List<RestaurantItem> fakeRestaurants = new ArrayList<>();

        RestaurantItem r1 = new RestaurantItem(
                "ID_1",
                "NAME_1",
                48.5242751,
                2.3842851,
                "VICINITY_1",
                createFakePhotos(),
                800,
                (float) 4.5,
                true,
                0
        );

        fakeRestaurants.add(r1);

        RestaurantItem r2 = new RestaurantItem(
                "ID_2",
                "NAME_2",
                48.9242751,
                2.6842851,
                "VICINITY_2",
                createFakePhotos(),
                600,
                (float) 2.3,
                false,
                0
        );

        fakeRestaurants.add(r2);

        return fakeRestaurants;
    }

    public static Location createFakeLocation() {
        Location location = new Location();
        location.setLat(48.9242751);
        location.setLng(2.6842851);
        return location;
    }



    public static List<Result> createApiResults() {
        ArrayList<Result> results = new ArrayList<>();

        Result result = new Result();
        result.setPlaceId("ID_1");

        result.setName("name");
        result.setRating(3.5);
        result.setIcon("icon");

        results.add(result);

        Result result1 = new Result();
        result1.setPlaceId("ID_2");

        result1.setName("name_2");
        result1.setRating(4.9);
        result1.setIcon("icon2");

        results.add(result1);

        Result result2 = new Result();
        result2.setPlaceId("ID_3");

        result2.setName("name3");
        result2.setRating(0.3);
        result2.setIcon("icon3");

        results.add(result2);

        return results;
    }


    private static List<Photo> createFakePhotos() {
        ArrayList<Photo> photo_0_list = new ArrayList<>();

        Photo photo_0 = new Photo();
        photo_0.setPhotoReference("Photo_0_REFERENCE");
        photo_0.setHtmlAttributions(null);
        photo_0.setHeight(300);
        photo_0.setWidth(400);

        photo_0_list.add(photo_0);
        return photo_0_list;
    }


}
