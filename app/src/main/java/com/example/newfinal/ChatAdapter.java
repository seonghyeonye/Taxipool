package com.example.newfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
    private Context mContext;
    private List<JSONObject> chats;
    PortToServer port;
    Gson gson = new Gson();

    public ChatAdapter(Context mContext, List<JSONObject> chats) {
        this.mContext = mContext;
        this.chats = chats;
        port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)mContext).cookies);
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("hohohoh");
        View baseView = View.inflate(mContext, R.layout.item_message_received,null);
        ChatHolder chatHolder = new ChatHolder (baseView);
        return chatHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatHolder holder, final int position) {
        JSONObject obj = chats.get(position);
        try {
            System.out.println("bindviewholder");
            System.out.println(obj);
            //holder.image_message_profile.setText(obj.getString("profile"));
            holder.text_message_name.setText(obj.getString("name"));
            holder.text_message_body.setText(obj.getString("body"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
