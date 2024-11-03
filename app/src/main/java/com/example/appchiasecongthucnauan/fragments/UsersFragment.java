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
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;
import com.example.appchiasecongthucnauan.models.search.UserSearchResultDto;
import com.example.appchiasecongthucnauan.utils.SearchState;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment implements SearchableFragment {
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_users, container, false);
            recyclerView = rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UsersAdapter();
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
            if (adapter != null && results.getUsers() != null) {
                List<User> users = new ArrayList<>();
                for (UserSearchResultDto userDto : results.getUsers()) {
                    users.add(new User(userDto.getName(), userDto.getId(), null));
                }
                adapter.setUsers(users);
            }
        });
    }
}