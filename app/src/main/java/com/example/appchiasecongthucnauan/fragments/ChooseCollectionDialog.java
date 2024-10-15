package com.example.appchiasecongthucnauan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.BookmarkCollection;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChooseCollectionDialog extends BottomSheetDialogFragment {
    private List<BookmarkCollection> collections;
    private OnCollectionSelectedListener listener;

    public ChooseCollectionDialog(List<BookmarkCollection> collections, OnCollectionSelectedListener listener) {
        this.collections = collections;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_choose_collection, container, false);
        RadioGroup radioGroup = view.findViewById(R.id.collectionsRadioGroup);

        for (BookmarkCollection collection : collections) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(collection.getName());
            radioButton.setId(View.generateViewId());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int index = radioGroup.indexOfChild(group.findViewById(checkedId));
            listener.onCollectionSelected(collections.get(index));
            dismiss();
        });

        return view;
    }

    public interface OnCollectionSelectedListener {
        void onCollectionSelected(BookmarkCollection collection);
    }
}