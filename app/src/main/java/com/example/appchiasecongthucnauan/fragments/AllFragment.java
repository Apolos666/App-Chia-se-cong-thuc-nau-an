package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.AllAdapter;
import com.example.appchiasecongthucnauan.models.Recipe_1;
import com.example.appchiasecongthucnauan.models.User;
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;
import com.example.appchiasecongthucnauan.models.search.RecipeSearchResultDto;
import com.example.appchiasecongthucnauan.models.search.UserSearchResultDto;

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment implements SearchableFragment {
    private RecyclerView recyclerView;
    private AllAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllAdapter();
        recyclerView.setAdapter(adapter);

        // fix sort cho tab all
        Button sortButton = view.findViewById(R.id.sortButton);
        sortButton.setOnClickListener(v -> {
            adapter.toggleSortOrder();
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onSearchResultsUpdated(SearchResultDto results) {
        List<Object> combinedResults = new ArrayList<>();
        
        // Thêm kết quả người dùng
        if (results.getUsers() != null) {
            for (UserSearchResultDto userDto : results.getUsers()) {
                combinedResults.add(new User(userDto.getName(), userDto.getId(), null));
            }
        }
        
        // Thêm kết quả công thức
        if (results.getRecipes() != null) {
            for (RecipeSearchResultDto recipeDto : results.getRecipes()) {
                combinedResults.add(new Recipe_1(recipeDto.getId(),recipeDto.getTitle(), recipeDto.getChefName(), recipeDto.getThumbnailUrl()));
            }
        }
        
        adapter.setItems(combinedResults);
    }
}