package com.example.newfinal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Fragment3_main extends Fragment {
    Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.selectmode, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView){
        final Button searchtaxi= rootView.findViewById(R.id.search_taxi);
        final Button mytaxi=rootView.findViewById(R.id.mytaxi);

        searchtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                        .replace(R.id.frameContainer, new Fragment3())
                        .addToBackStack(null)
                        .commit();
            }
        });
        mytaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                        .replace(R.id.frameContainer,new chat_list())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
