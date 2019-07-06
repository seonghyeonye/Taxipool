package com.example.newfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Fragment3 extends Fragment {

    public static CharSequence startpoint;
    public static CharSequence endpoint;
    Spinner spinner;
    Spinner spinner1;
    Context context;
    OnTabItemSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.taxi, container, false);

        initUI(rootView);

        return rootView;
    }


    private void initUI(final ViewGroup rootView) {
        spinner = rootView.findViewById(R.id.startpoint);
        spinner1 = rootView.findViewById(R.id.endpoint);
        TextView textView= rootView.findViewById(R.id.date);
        Button showtaxi = rootView.findViewById(R.id.showtaxi);
        setSpinner(spinner);
        setSpinner(spinner1);
        startpoint= spinner.getSelectedItem().toString();
        endpoint=spinner1.getSelectedItem().toString();
        //startpoint=((TextView) spinner.getSelectedView()).getText();
        //endpoint=((TextView) spinner.getSelectedView()).getText();
        showtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
                        .replace(R.id.frameContainer, new Taxi())
                        .addToBackStack(null)
                        .commit();
            }
        });
        FloatingActionButton swap= rootView.findViewById(R.id.swap);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startpoint=((TextView) spinner.getSelectedView()).getText();
                        endpoint=((TextView) spinner1.getSelectedView()).getText();
                //System.out.println(((TextView) spinner.getSelectedView()).getText());
                        ((TextView) spinner.getSelectedView()).setText(endpoint);
                        ((TextView) spinner1.getSelectedView()).setText(startpoint);
                        CharSequence temp;
                        temp=startpoint;
                        startpoint=endpoint;
                        endpoint=temp;
            }
        });
        long now = System.currentTimeMillis();
        Date dateform= new Date(now);
        System.out.println(dateform);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm");
        String getTime=sdf.format(dateform);
        textView.setText(getTime);

    }
    private void setSpinner(final Spinner spinners){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.places,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text =adapterView.getItemAtPosition(i).toString();
                if(spinners==spinner){
                    startpoint=text;
                }
                else{
                    endpoint=text;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinners.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinners.getSelectedView()).setTextSize(30);
            }
        });
    }


}