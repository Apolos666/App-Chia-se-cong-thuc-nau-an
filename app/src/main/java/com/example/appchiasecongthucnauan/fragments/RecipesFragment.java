package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.RecipesAdapter;
import com.example.appchiasecongthucnauan.models.Recipe_1;
import com.example.appchiasecongthucnauan.models.User;

import java.util.ArrayList;
import java.util.List;

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

        List<Recipe_1> sampleData = new ArrayList<>();
        sampleData.add(new Recipe_1("Bánh mì", "Đầu bếp D", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));


        // Cập nhật adapter với dữ liệu mẫu
        adapter.setRecipes(sampleData);

        Button sortButton = view.findViewById(R.id.sortButton);
        sortButton.setOnClickListener(v -> {
            adapter.toggleSortOrder();
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    public void updateSearchResults(String query) {
        // Implement search logic here
        // Update adapter with new search results
        // adapter.setItems(newSearchResults);
    }
}