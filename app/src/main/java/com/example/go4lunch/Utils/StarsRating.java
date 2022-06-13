package com.example.go4lunch.Utils;

import android.view.View;
import android.widget.ImageView;

public class StarsRating {

    public static void calculateRating(Float rating, ImageView firstStar, ImageView secondStar, ImageView thirdStar) {
        double max = 4;
        double half = 2.5;
        double min = 1.5;

        if (rating == null) {
            firstStar.setVisibility(View.GONE);
            secondStar.setVisibility(View.GONE);
            thirdStar.setVisibility(View.GONE);
        } else if (rating > min && rating < half) {
            firstStar.setVisibility(View.VISIBLE);
            secondStar.setVisibility(View.GONE);
            thirdStar.setVisibility(View.GONE);
        } else if (rating > half && rating < max) {
            firstStar.setVisibility(View.VISIBLE);
            secondStar.setVisibility(View.VISIBLE);
            thirdStar.setVisibility(View.GONE);
        } else if (rating > max) {
            firstStar.setVisibility(View.VISIBLE);
            secondStar.setVisibility(View.VISIBLE);
            thirdStar.setVisibility(View.VISIBLE);
        }
    }

}
