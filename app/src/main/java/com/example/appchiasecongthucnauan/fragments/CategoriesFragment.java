package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.CategoryAdapter;
import com.example.appchiasecongthucnauan.models.CategoryItem;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.GridLayoutManager;

public class CategoriesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<CategoryItem> items = new ArrayList<>();
        items.add(new CategoryItem("Italian"));
        items.add(new CategoryItem("Mexican"));
        items.add(new CategoryItem("Italian"));
        items.add(new CategoryItem("Mexican"));
        items.add(new CategoryItem("Italian"));
        items.add(new CategoryItem("Mexican"));

        CategoryAdapter adapter = new CategoryAdapter(items);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
