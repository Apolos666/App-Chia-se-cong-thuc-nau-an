package com.example.appchiasecongthucnauan.fragments;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.TrendingAdapter;
import com.example.appchiasecongthucnauan.models.explore.TrendingRecipeDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;
import com.example.appchiasecongthucnauan.apis.ApiService;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment {
    private RecyclerView recyclerView;
    private TrendingAdapter adapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        apiService = RetrofitClient.getInstance().getApiService();
        
        loadTrendingRecipes();
        
        return view;
    }

    private void loadTrendingRecipes() {
        apiService.getTrendingRecipes(10).enqueue(new Callback<List<TrendingRecipeDto>>() {
            @Override
            public void onResponse(Call<List<TrendingRecipeDto>> call, Response<List<TrendingRecipeDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new TrendingAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TrendingRecipeDto>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
