package com.example.go4lunch.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestaurantsListViewModel restaurantsListViewModel;
    List<RestaurantItem> data = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String userLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        restaurantsListViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance()).get(RestaurantsListViewModel.class);

        recyclerView = view.findViewById(R.id.fragment_main_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
        });
        restaurantsListViewModel.initList(userLocation);

        restaurantsListViewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurantItems -> {
            if (restaurantItems != null) {
                data.clear();
                data.addAll(restaurantItems);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
