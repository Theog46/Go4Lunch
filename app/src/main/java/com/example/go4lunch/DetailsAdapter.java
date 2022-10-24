package com.example.go4lunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.User;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsHolder> {

    private final List<User> workmatesList;
    final Context context;

    public DetailsAdapter(Context context, List<User> workmatesList) {
        this.context = context;
        this.workmatesList = workmatesList;
    }



    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listview_item_details_item, parent, false);
        v.getContext();
        return new DetailsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
        holder.createViewWithWorkmates(workmatesList.get(position));

    }



    @Override
    public int getItemCount() {
        return workmatesList.size();
    }
}
