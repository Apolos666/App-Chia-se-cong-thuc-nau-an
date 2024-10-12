package com.example.appchiasecongthucnauan.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.example.appchiasecongthucnauan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomBottomNavigationView extends BottomNavigationView {
    public CustomBottomNavigationView(Context context) {
        super(context);
        init();
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(android.R.color.white));
        setElevation(8);
    }
}
