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
    MainActivity mainActivity;
    int id;
    BottomNavigationView bottomNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    System.out.println("okay");
                    switch (item.getItemId()) {
                        case R.id.tab1:
                            mainActivity.currentTab = 0;
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment1).commit();
                            return true;
                        case R.id.tab2:
                            System.out.println("pass2");
                            mainActivity.currentTab = 1;
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment2).commit();

                            return true;
                        case R.id.tab3:
                            mainActivity.currentTab = 2;
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment3).commit();

                            return true;
                    }

                    return false;
                }
            };

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("mainfrag on detatch");
        Fragment topFrag;
        System.out.println(bottomNavigation.getSelectedItemId());
        System.out.println(R.id.tab1);
        System.out.println(R.id.tab2);
        System.out.println(R.id.tab3);
        switch (bottomNavigation.getSelectedItemId()){
            case R.id.tab2:
                topFrag = fragment2;
                break;
            case R.id.tab3:
                topFrag = fragment3;
                break;
            default:
                topFrag = fragment1;
                break;
        }
        mainActivity.getSupportFragmentManager().beginTransaction().remove(topFrag).commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("main frag on create");
        //fragment1 = new Fragment1();
        fragment1 = ((MainActivity)getActivity()).fragment1;
        fragment2 = ((MainActivity)getActivity()).fragment2;
        fragment3 = ((MainActivity)getActivity()).fragment3;
        mainActivity = ((MainActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("main frag on create view");
        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.mainfragment, container, false );
        bottomNavigation = view.findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        System.out.println("main frag on create view");
        Fragment initialFrag;
        System.out.println(bottomNavigation.getSelectedItemId());
        System.out.println(R.id.tab1);
        System.out.println(R.id.tab2);
        System.out.println(R.id.tab3);
        switch (mainActivity.currentTab){
            case 1:
                initialFrag = fragment2;
                break;
            case 2:
                initialFrag = fragment3;
                break;
            default:
                initialFrag = fragment1;
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFrag).commit();
        return view;
    }


    public void onTabSelected(int position) {
        if (position == 0) {
            mainActivity.currentTab = 0;
            System.out.println(mainActivity.currentTab);
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            mainActivity.currentTab = 1;
            System.out.println(mainActivity.currentTab);
            bottomNavigation.setSelectedItemId(R.id.tab2);
        } else if (position == 2) {
            mainActivity.currentTab = 2;
            System.out.println(mainActivity.currentTab);
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }
    }
}
