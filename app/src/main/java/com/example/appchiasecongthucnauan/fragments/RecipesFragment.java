package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.RecipesAdapter;

public class RecipesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecipesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipesAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void updateSearchResults(String query) {
        // Implement search logic here
        // Update adapter with new search results
        // adapter.setItems(newSearchResults);
    }
}