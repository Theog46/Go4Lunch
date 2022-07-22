package com.example.go4lunch.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.ViewModel.RestaurantItem;
import com.example.go4lunch.ViewModel.RestaurantsListViewModel;
import com.example.go4lunch.ViewModelFactory;
import com.example.go4lunch.fragments.ListView.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestaurantsListViewModel restaurantsListViewModel;
    List<RestaurantItem> data = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);


        restaurantsListViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance()).get(RestaurantsListViewModel.class);


        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);



        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);

        //* TODO ARRANGER POUR PASSER USERLOCATION *//
        

        restaurantsListViewModel.initList("37.4219983%2C-122.084");
        restaurantsListViewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurantItems -> {
            if (restaurantItems != null) {
                data.addAll(restaurantItems);
                adapter.notifyDataSetChanged();
                Log.d("AH", String.valueOf(restaurantItems));
                Log.d("BAH", String.valueOf(data));

            }
        });

        return view;
    }





}
