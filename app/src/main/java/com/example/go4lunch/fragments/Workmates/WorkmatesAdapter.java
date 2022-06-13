package com.example.go4lunch.fragments.Workmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;

import java.util.List;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesHolder> {

    List<String> arr;

    public WorkmatesAdapter(List<String> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public WorkmatesAdapter.WorkmatesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkmatesAdapter.WorkmatesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmates_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesAdapter.WorkmatesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    class WorkmatesHolder extends RecyclerView.ViewHolder {

        TextView txt;

        public WorkmatesHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.workmate_name);
        }
    }
}
