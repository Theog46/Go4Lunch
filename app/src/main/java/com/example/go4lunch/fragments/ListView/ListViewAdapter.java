package com.example.go4lunch.fragments.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.ViewModel.RestaurantItem;
import com.example.go4lunch.fragments.DetailsFragment;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private final List<RestaurantItem> restaurantItems;
    final Context context;

    public ListViewAdapter(Context context, List<RestaurantItem> restaurantItems) {
        this.context = context;
        this.restaurantItems = restaurantItems;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listview_item, parent, false);
        v.getContext();
        return new ListViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        holder.createViewRestaurants(restaurantItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailsFragment detailsFragment = new DetailsFragment();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_drawer_layout, detailsFragment)
                            .addToBackStack(null)
                            .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantItems.size();
    }
    


}
