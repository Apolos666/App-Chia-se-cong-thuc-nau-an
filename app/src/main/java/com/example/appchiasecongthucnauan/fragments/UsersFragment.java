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
import com.example.appchiasecongthucnauan.adapters.UsersAdapter;
import com.example.appchiasecongthucnauan.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UsersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UsersAdapter();
        recyclerView.setAdapter(adapter);

        List<User> sampleData = new ArrayList<>();
        sampleData.add(new User("Nguyễn Văn A", "nguyenvana", "url_hinh_anh"));
        sampleData.add(new User("Trần Thị C", "tranthic", "url_hinh_anh"));

        // Update adapter with sample data
        adapter.setUsers(sampleData);

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