package com.example.newfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class taxi_register extends Fragment {
    EditText name;
    EditText taxitime;
    EditText limitperson;
    EditText info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.add_taxi, container, false);
        initUI(rootView);
        return rootView;
    }
    private void initUI(final ViewGroup rootView) {
        LinearLayout layout = rootView.findViewById(R.id.addtaxi);
        Button buttoncancel= rootView.findViewById(R.id.cancelBtn);
        layout.setBackground(getActivity().getDrawable(R.drawable.round_bg));
        Button buttonInsert = (Button)rootView.findViewById(R.id.confirmBtn);
        name = rootView.findViewById(R.id.username);
        taxitime = (EditText) rootView.findViewById(R.id.taxitime);
        limitperson = (EditText) rootView.findViewById(R.id.limitpeople);
        info = (EditText) rootView.findViewById(R.id.other);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 읽기
                System.out.println("inserted");
                String getName = name.getText().toString();
                String getTime = taxitime.getText().toString();
                String getnumber = limitperson.getText().toString();
                String getinfo = info.getText().toString();

                // Pattern match for email id
                // Check if all strings are null or not
                if (getName.equals("") || getName.length() == 0
                        || getTime.equals("") || getTime.length() == 0
                        || getnumber.equals("") || getnumber.length() == 0
                        || getinfo.equals("")
                        || getinfo.length() == 0)

                    new CustomToast().Show_Toast(getActivity(), rootView,
                            "All fields are required.");

            }
        });
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}
