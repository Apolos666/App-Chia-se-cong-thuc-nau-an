package com.example.appchiasecongthucnauan.explore;

import com.example.appchiasecongthucnauan.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<TrendingItem> items = new ArrayList<>();
        items.add(new TrendingItem("Summer Salad", "Chef Maria"));
        items.add(new TrendingItem("BBQ Ribs", "Chef Maria"));
        items.add(new TrendingItem("Vegan Burger", "Chef Maria"));
        items.add(new TrendingItem("Sushi Bowl", "Chef Maria"));

        TrendingAdapter adapter = new TrendingAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Sử dụng requireContext()
        recyclerView.setAdapter(adapter);

        return view;
    }
}
