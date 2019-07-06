package com.example.newfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    RecyclerView recyclerView;

    Context context;
    OnTabItemSelectedListener listener;
    List<Contact> contacts = new ArrayList<>();
    FloatingActionButton buttonAdd;
    ViewGroup rootView;

    public ViewGroup getRootView() {
        return rootView;
    }

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
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        initUI(rootView);
        buttonAdd = rootView.findViewById(R.id.plus2);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContacts();
            }
        });
        return rootView;
    }

    private void addContacts(){
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new PhoneBookRegisterFrag()).commit();
    }

    public void initUI(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.plant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final PostAdapter adapter = new PostAdapter(context, contacts);
        recyclerView.setAdapter(adapter);
        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000");
            QueryToServer queryS;
            BasicDBObject query = new BasicDBObject().append("account._id", "myhwang99");
            JSONArray queries = new JSONArray().put(query);
            queryS = new QueryToServerMongo("madcamp", "contacts", "/crud/research", new JSONArray().put(query));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONObject found = (JSONObject) respond.getJSONArray("data").get(0);
                        List<Contact> contactList = (List<Contact>) gson.fromJson(found.getString("contacts"), new TypeToken<List<Contact>>() {
                        }.getType());
                        System.out.println("ok");
                        contacts.clear();
                        contacts.addAll(contactList);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

}
