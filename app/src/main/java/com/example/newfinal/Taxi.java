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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Taxi extends Fragment {
    Context context;
    RecyclerView recyclerView;
    List<Taxitime> taxitime = new ArrayList<>();

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

        //Bundle bundle=this.getArguments();
        //if(bundle!=null){

       // }

        recyclerView = rootView.findViewById(R.id.show_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final PtaxiAdapter adapter = new PtaxiAdapter(context, taxitime);
        recyclerView.setAdapter(adapter);

        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000");
            QueryToServer queryS;
            BasicDBObject query = new BasicDBObject().append("account._id", "myhwang99");
            JSONArray queries = new JSONArray().put(query);
            queryS = new QueryToServerMongo("madcamp", "taxi", "/crud/research", new JSONArray().put(query));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONObject found = (JSONObject) respond.getJSONArray("data").get(0);
                        List<Taxitime> newTaxi = (List<Taxitime>) gson.fromJson(found.getString("taxi"), new TypeToken<List<Taxitime>>() {
                        }.getType());
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
        adapter.notifyDataSetChanged();

        //System.out.println(startpoint2.getText());
        startpoint2.setText(Fragment3.startpoint);
        endpoint2.setText(Fragment3.endpoint);

        CharSequence date= Fragment3.textView.getText();
        try {
            Date dateform = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm").parse((String) date);
            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
            String getTime=sdf.format(dateform);
            today.setText(getTime);
        }
        catch (Exception e){
            e.printStackTrace();
        }

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