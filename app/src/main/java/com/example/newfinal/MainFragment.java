package com.example.newfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment implements OnTabItemSelectedListener {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3_main fragment3;

    BottomNavigationView bottomNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    System.out.println("okay");
                    switch (item.getItemId()) {
                        case R.id.tab1:
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment1).commit();

                            return true;
                        case R.id.tab2:
                            System.out.println("pass2");
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment2).commit();

                            return true;
                        case R.id.tab3:
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment3).commit();

                            return true;
                    }

                    return false;
                }
            };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //fragment1 = new Fragment1();
        fragment1 = ((MainActivity)getActivity()).fragment1;
        fragment2 = ((MainActivity)getActivity()).fragment2;
        fragment3 = ((MainActivity)getActivity()).fragment3;


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.mainfragment, container, false );
        bottomNavigation = view.findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }


    public void onTabSelected(int position) {
        if (position == 0) {
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            bottomNavigation.setSelectedItemId(R.id.tab2);
        } else if (position == 2) {
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }
    }
}
