package com.example.newfinal;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Taxi extends Fragment {
    Context context;
    RecyclerView recyclerView;
    List<Taxitime> taxitime = new ArrayList<>();
    public static String getTime;
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = ((MainActivity)getActivity());
        rootView = (ViewGroup) inflater.inflate(R.layout.show_taxi, container, false);
        initUI(rootView);
        return rootView;
    }
    private void initUI(final ViewGroup rootView) {
        TextView startpoint2= rootView.findViewById(R.id.startpoint2);
        TextView endpoint2 = rootView.findViewById(R.id.endpoint2);
        TextView today = rootView.findViewById(R.id.today);
        FloatingActionButton plus3= rootView.findViewById(R.id.plus3);
        ImageView backshowtaxi= rootView.findViewById(R.id.backshowtaxi);

        backshowtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.left_in,R.anim.right_out)
                        .replace(R.id.frameContainer, new Fragment3())
                        .addToBackStack(null)
                        .commit();
            }
        });

        CharSequence date= Fragment3.textView.getText();
        try {
            Date dateform = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm").parse((String) date);
            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
            getTime=sdf.format(dateform);
            today.setText(getTime);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //Bundle bundle=this.getArguments();
        //if(bundle!=null){

       // }

//        recyclerView = rootView.findViewById(R.id.show_time);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        final PtaxiAdapter adapter = new PtaxiAdapter(context, taxitime);
//        recyclerView.setAdapter(adapter);

        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServer queryS;
            BasicDBObject query = new BasicDBObject();
            queryS = new QueryToServerMongo("madcamp", "taxi_public", "/crud/research", new JSONArray().put(query));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONArray found = respond.getJSONArray("data");
                        List <Taxitime> newTaxi = new ArrayList<>();
                        for (int i = 0; i < found.length(); i++){
                            System.out.println("ASDFASDFASDFASDF");
                            try {
                                Taxitime time = gson.fromJson(found.getJSONObject(i).getString("taxi"), Taxitime.class);
                                if(Arrays.asList(time.participators).contains(((MainActivity)context).account.getString("name"))){
                                    time.enter = "yes";
                                } else {
                                    time.enter = "NO";
                                }
                                newTaxi.add(time);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        System.out.println("ok");
                        taxitime.clear();
                        taxitime.addAll(newTaxi);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("asd");
//        adapter.notifyDataSetChanged();
        recyclerView = rootView.findViewById(R.id.show_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        System.out.println("asd2");
        final PtaxiAdapter adapter = new PtaxiAdapter(context, taxitime);

        recyclerView.setAdapter(adapter);
        System.out.println("asd2");
        //System.out.println(startpoint2.getText());
        startpoint2.setText(Fragment3.startpoint);
        endpoint2.setText(Fragment3.endpoint);

        System.out.println("asd3");

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

    public void alert(){
        new CustomToast().Show_Toast(getActivity(), rootView,
                "All fields are required.");
    }
}