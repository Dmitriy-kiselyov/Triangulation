package com.example.grafica.lab4;

import android.support.v4.app.Fragment;

public class DrawPolygonActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new DrawPolygonFragment();
    }
}
