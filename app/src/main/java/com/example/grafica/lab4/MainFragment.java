package com.example.grafica.lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sex_predator on 01.03.2016.
 */
public class MainFragment extends Fragment {

    private static final int REQUEST_POLYGON = 1;

    private PolygonView mPolygonView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mPolygonView = (PolygonView) v.findViewById(R.id.polygon_main);
        mPolygonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolygonView.incTriangles();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset:
                mPolygonView.resetTriangles();
                return true;
            case R.id.menu_new_polygon:
                Intent intent = new Intent(getContext(), DrawPolygonActivity.class);
                startActivityForResult(intent, REQUEST_POLYGON);
                return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_POLYGON:
                mPolygonView.reset();
        }
    }

}
