package com.example.go4lunch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.fragments.Workmates.WorkmatesAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesFragment extends Fragment {

    private List<String> arr;
    private WorkmatesAdapter workmatesAdapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates, container, false);

        recyclerView = view.findViewById(R.id.fragment_workmates_recycler_view);

        arr = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            arr.add(Integer.toString(i).concat(".Restaurant Name"));
        }

        workmatesAdapter = new WorkmatesAdapter(arr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workmatesAdapter);

        return view;


    }
}
