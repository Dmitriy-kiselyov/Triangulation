package com.example.grafica.lab4;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class DrawPolygonFragment extends Fragment {

    private PolygonDrawView mPolygonDrawView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_draw_polygon, container, false);

        mPolygonDrawView = (PolygonDrawView) v.findViewById(R.id.polygon_draw);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_draw_polygon, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_remove:
                mPolygonDrawView.remove();
                return true;
            case R.id.menu_clear:
                mPolygonDrawView.clear();
                return true;
            case R.id.menu_finish:
                if (mPolygonDrawView.finish()) {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
                return true;
        }

        return false;
    }
}
