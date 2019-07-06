package com.example.newfinal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Taxi extends Fragment {
    Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.show_taxi, container, false);
        initUI(rootView);
        return rootView;
    }
    private void initUI(final ViewGroup rootView) {
        TextView startpoint2= rootView.findViewById(R.id.startpoint2);
        TextView endpoint2 = rootView.findViewById(R.id.endpoint2);
        TextView today = rootView.findViewById(R.id.today);
        FloatingActionButton plus3= rootView.findViewById(R.id.plus3);


        System.out.println(startpoint2.getText());
        startpoint2.setText(Fragment3.startpoint);
        endpoint2.setText(Fragment3.endpoint);

        long now = System.currentTimeMillis();
        Date dateform= new Date(now);
        System.out.println(dateform);
        SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
        String getTime=sdf.format(dateform);
        today.setText(getTime);

        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                        .replace(R.id.frameContainer, new taxi_register())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}