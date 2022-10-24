package com.example.go4lunch.fragments.ListView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Maps.Photo;
import com.example.go4lunch.R;
import com.example.go4lunch.Utils.StarsRating;
import com.example.go4lunch.ViewModel.RestaurantItem;


public class ListViewHolder extends RecyclerView.ViewHolder {

    private final TextView restaurantName = itemView.findViewById(R.id.restaurant_name);
    private final TextView restaurantVicinity = itemView.findViewById(R.id.restaurant_address);
    private final TextView isRestaurantOpen = itemView.findViewById(R.id.restaurant_hours);
    private final ImageView restaurantImage = itemView.findViewById(R.id.restaurant_picture);
    private final ImageView firstStar = itemView.findViewById(R.id.star_1);
    private final ImageView secondStar = itemView.findViewById(R.id.star_2);
    private final ImageView thirdStar = itemView.findViewById(R.id.star_3);
    private final TextView  range = itemView.findViewById(R.id.restaurant_range);
    private final TextView workmatesNumber = itemView.findViewById(R.id.workmate_number);
    private final ImageView workmatesImg = itemView.findViewById(R.id.workmate_icon);



    public ListViewHolder(View itemView) {
        super(itemView);
    }

    public void createViewRestaurants(RestaurantItem restaurantItem) {
        restaurantName.setText(restaurantItem.getName());
        restaurantVicinity.setText(restaurantItem.getVicinity());

        if (restaurantItem.getWorkmates_number() != 0) {
            workmatesNumber.setText(String.valueOf("(" + restaurantItem.getWorkmates_number() + ")"));
            workmatesNumber.setVisibility(View.VISIBLE);
            workmatesImg.setVisibility(View.VISIBLE);
        } if (restaurantItem.getWorkmates_number() == 0) {
            workmatesNumber.setVisibility(View.INVISIBLE);
            workmatesImg.setVisibility(View.INVISIBLE);
        }

        if (restaurantItem.getOpeningHours()) {
            isRestaurantOpen.setText(R.string.open);
            isRestaurantOpen.setTypeface(null, Typeface.ITALIC);
            isRestaurantOpen.setTextColor(Color.rgb(128, 128, 128));
        }
        if (!restaurantItem.getOpeningHours()) {
            isRestaurantOpen.setText(R.string.close);
            isRestaurantOpen.setTypeface(null, Typeface.NORMAL);
            isRestaurantOpen.setTextColor(Color.rgb(161, 16, 3));
        }

        StarsRating.calculateRating(restaurantItem.getRating(), firstStar, secondStar, thirdStar);

        displayImage(restaurantItem);

        range.setText(restaurantItem.getRange() + "m");



    }
    private void displayImage(RestaurantItem restaurantItem) {
        Context ctx = itemView.getContext();
        String KEY = ctx.getResources().getString(R.string.google_maps_key);

        if (restaurantItem.getPictures() == null) {
            restaurantImage.setVisibility(View.INVISIBLE);
        } else {
            Photo photo = restaurantItem.getPictures().get(0);
            Glide.with(this.itemView.getContext())
                    .load(photo.getUrl() + KEY)
                    .apply(new RequestOptions()
                            .centerCrop())
                    .into(restaurantImage);
            restaurantImage.setVisibility(View.VISIBLE);
        }
    }





}
