package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.RecentAdapter;
import com.example.appchiasecongthucnauan.models.RecentItem;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<RecentItem> items = new ArrayList<>();
        items.add(new RecentItem("Mango Lassi", "Chef Alex", "2 hours ago"));
        items.add(new RecentItem("Greek Mousakka", "Chef Alex", "2 hours ago"));
        items.add(new RecentItem("Pho", "Chef Alex", "2 hours ago"));
        items.add(new RecentItem("Tacos al Pastor", "Chef Alex", "2 hours ago"));

        RecentAdapter adapter = new RecentAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
