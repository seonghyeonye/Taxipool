package com.example.newfinal;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import org.json.JSONArray;

import java.io.IOException;

public class taxi_register extends Fragment {
    EditText name;
    EditText taxitime;
    EditText limitperson;

    private OnFragmentInteractionListener2 mListener;
    PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
    QueryToServerMongoBuilder builderTaxi = new QueryToServerMongoBuilder("madcamp", "taxi");

    public taxi_register(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.add_taxi, container, false);
        initUI(rootView);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void initUI(final ViewGroup rootView) {
        LinearLayout layout = rootView.findViewById(R.id.addtaxi);
        Button buttoncancel= rootView.findViewById(R.id.cancelBtn);
        layout.setBackground(getActivity().getDrawable(R.drawable.round_bg));
        Button buttonInsert = (Button)rootView.findViewById(R.id.confirmBtn);
        name = rootView.findViewById(R.id.username);
        taxitime = (EditText) rootView.findViewById(R.id.taxitime);
        limitperson = (EditText) rootView.findViewById(R.id.limitpeople);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 읽기
                System.out.println("inserted");
                String _Name = name.getText().toString();
                String _Time = taxitime.getText().toString();
                String _Number = limitperson.getText().toString();

                // Pattern match for email id
                // Check if all strings are null or not
                if (_Name.equals("") || _Name.length() == 0
                        || _Time.equals("") || _Time.length() == 0
                        || _Number.equals("") || _Number.length() == 0)

                    new CustomToast().Show_Toast(getActivity(), rootView,
                            "All fields are required.");
                else {
                    String[] data = {_Name, _Time, _Number};
                    Taxitime taxitime = new Taxitime(data[0], data[1], data[2]);
                    try {
                        port.postToServerV2(builderTaxi.getQueryU(new JSONArray().put(QueryBuilder.start("account._id").is("myhwang99").get()).put(QueryBuilder.start("$push").is(QueryBuilder.start("taxi").is((BasicDBObject) JSON.parse(taxitime.toString())).get()).get())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        });

        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }
    public interface OnFragmentInteractionListener2 {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void finish(){
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,((MainActivity)getActivity()).taxi).commit();
    }
}
