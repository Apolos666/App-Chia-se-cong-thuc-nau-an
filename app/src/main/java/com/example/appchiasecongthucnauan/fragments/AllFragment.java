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
import com.example.appchiasecongthucnauan.adapters.AllAdapter;
import com.example.appchiasecongthucnauan.models.Recipe_1;
import com.example.appchiasecongthucnauan.models.User;

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {
    private RecyclerView recyclerView;
    private AllAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllAdapter();
        recyclerView.setAdapter(adapter);

        List<Object> sampleData = new ArrayList<>();
        sampleData.add(new User("Nguyễn Văn A", "nguyenvana", "url_hinh_anh"));
        sampleData.add(new User("Trần Thị C", "tranthic", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Bánh mì", "Đầu bếp D", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));
        sampleData.add(new Recipe_1("Phở bò", "Đầu bếp B", "url_hinh_anh"));


// Cập nhật adapter với dữ liệu mẫu
        adapter.setItems(sampleData);

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