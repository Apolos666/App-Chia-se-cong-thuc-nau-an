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
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;
import com.example.appchiasecongthucnauan.models.search.RecipeSearchResultDto;
import com.example.appchiasecongthucnauan.utils.SearchState;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment implements SearchableFragment {
    private RecyclerView recyclerView;
    private RecipesAdapter adapter;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
            recyclerView = rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new RecipesAdapter();
            recyclerView.setAdapter(adapter);

            SearchResultDto savedResults = SearchState.getInstance().getLastSearchResults();
            if (savedResults != null) {
                onSearchResultsUpdated(savedResults);
            }

            Button sortButton = rootView.findViewById(R.id.sortButton);
            sortButton.setOnClickListener(v -> {
                adapter.toggleSortOrder();
                adapter.notifyDataSetChanged();
            });
        }
        return rootView;
    }

    @Override
    public void onSearchResultsUpdated(SearchResultDto results) {
        if (getActivity() == null) return;

        getActivity().runOnUiThread(() -> {
            if (adapter != null && results.getRecipes() != null) {
                List<Recipe_1> recipes = new ArrayList<>();
                for (RecipeSearchResultDto recipeDto : results.getRecipes()) {
                    recipes.add(new Recipe_1(recipeDto.getTitle(), recipeDto.getChefName(), recipeDto.getThumbnailUrl()));
                }
                adapter.setRecipes(recipes);
            }
        });
    }
}