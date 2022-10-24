package com.example.go4lunch.fragments.Workmates;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.User;
import com.example.go4lunch.R;

public class WorkmatesHolder extends RecyclerView.ViewHolder {

    private final TextView username = itemView.findViewById(R.id.workmate_name);
    private final ImageView picture = itemView.findViewById(R.id.workmate_picture);
    private final TextView workmateState = itemView.findViewById(R.id.workmate_restaurant_name);
    private final TextView stateToRemove = itemView.findViewById(R.id.workmate_state);
    private final Context context;
    private final int color = Color.rgb(128, 128, 128);

    public WorkmatesHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void createViewWithWorkmates(User user) {
        username.setText(user.getUsername());
        Glide.with(context)
                .load(user.getUrlPicture())
                .apply(new RequestOptions()
                        .circleCrop())
                .into(picture);
        if (user.getRestaurantName() != null) {
            workmateState.setText(user.getRestaurantName());
        } if (!user.getIsEating()) {
            stateToRemove.setVisibility(View.GONE);
            workmateState.setText(R.string.not_decided);
            workmateState.setTextColor(color);
            username.setTextColor(color);
            username.setTypeface(null, Typeface.ITALIC);
        }

    }


}
