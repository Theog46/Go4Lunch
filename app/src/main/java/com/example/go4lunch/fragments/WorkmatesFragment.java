package com.example.go4lunch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.User;
import com.example.go4lunch.R;
import com.example.go4lunch.ViewModel.FirestoreViewModel;
import com.example.go4lunch.ViewModelFactory;
import com.example.go4lunch.fragments.Workmates.WorkmatesAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesFragment extends Fragment {

    WorkmatesAdapter adapter;
    private RecyclerView recyclerView;
    List<User> data = new ArrayList<>();
    private FirestoreViewModel firestoreViewModel;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates, container, false);

        firestoreViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance()).get(FirestoreViewModel.class);

        recyclerView = view.findViewById(R.id.fragment_workmates_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new WorkmatesAdapter(getActivity(), data);

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        firestoreViewModel.getAllWorkmates().observe(requireActivity(), workmates -> {
            data.clear();
            data.addAll(workmates);
        });

        adapter.notifyDataSetChanged();

        return view;
    }
}
