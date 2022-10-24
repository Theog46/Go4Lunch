package com.example.go4lunch;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.User;

public class DetailsHolder extends RecyclerView.ViewHolder {

    private final TextView username = itemView.findViewById(R.id.workmate_name);
    private final ImageView picture = itemView.findViewById(R.id.workmate_picture);
    private final Context context;

    public DetailsHolder(@NonNull View itemView) {
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
    }


}
