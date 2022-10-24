package com.example.go4lunch.fragments.Workmates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.User;
import com.example.go4lunch.R;

import java.util.List;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesHolder> {

    private final List<User> workmatesList;
    final Context context;

    public WorkmatesAdapter(Context context, List<User> workmatesList) {
        this.context = context;
        this.workmatesList = workmatesList;
    }

    @NonNull
    @Override
    public WorkmatesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmates_item, parent, false);
        v.getContext();
        return new WorkmatesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesHolder holder, int position) {
        holder.createViewWithWorkmates(workmatesList.get(position));
    }

    @Override
    public int getItemCount() {
        return workmatesList.size();
    }


}

