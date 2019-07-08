package com.example.newfinal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class myjoin extends Fragment {
    Context context;
    RecyclerView recyclerView;
    List<Taxitime> taxitime = new ArrayList<>();
    List<Taxitime> myjoin = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.joinfrg, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView){
        System.out.println("enter myjoin");

        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServer queryS;
            BasicDBObject query = new BasicDBObject();
            queryS = new QueryToServerMongo("madcamp", "taxi", "/crud/research", new JSONArray().put(query));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONArray found = respond.getJSONArray("data");
                        List <Taxitime> newTaxi = new ArrayList<>();
                        for (int i = 0; i < found.length(); i++){
                            newTaxi.add(gson.fromJson(found.getJSONObject(i).getString("taxi"), Taxitime.class));
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

        for(int i=0;i<taxitime.size();i++){
            Taxitime taxiitem= taxitime.get(i);
            if(taxiitem.enter.equals("yes")){
                myjoin.add(taxiitem);
            }
        }

        RecyclerView recyclerView;
        recyclerView = rootView.findViewById(R.id.show_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final PtaxiAdapter adapter = new PtaxiAdapter(context, myjoin);
        recyclerView.setAdapter(adapter);
    }
}
