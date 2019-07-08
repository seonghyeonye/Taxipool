package com.example.newfinal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class chat_list extends Fragment {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.chat_list, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView){
        TabLayout tablist  = rootView.findViewById(R.id.tabs);
        ImageView backchatlist = rootView.findViewById(R.id.backchatlist);
        backchatlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.right_out)
                        .replace(R.id.frameContainer, new Fragment3_main())
                        .addToBackStack(null)
                        .commit();
            }
        });
        tablist.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.chatContainer, new myregister()).commit();
                        System.out.println("enter one");
                    case 1:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.chatContainer, new myjoin()).commit();
                        System.out.println("enter two");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
