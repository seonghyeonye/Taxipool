package com.example.newfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private Context mContext;
    private List<Contact> contacts;
    PortToServer port;
    Gson gson = new Gson();

    public PostAdapter(Context mContext, List<Contact> contacts) {
        this.mContext = mContext;
        this.contacts = contacts;
        port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)mContext).cookies);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.phonebook_item,null);
        PostViewHolder postViewHolder=new PostViewHolder(baseView);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        Contact contact = contacts.get(position);
        holder.ivname.setText(contact.getName());
        holder.ivnumber.setText(contact.getPhoneNumber());
        holder.ivemail.setText(contact.getEmail());
        holder.ivrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm Message")
                        .setMessage("연락처를 삭제하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Contact removed = contacts.remove(position);
                                Toast.makeText(mContext.getApplicationContext(), "연락처가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                QueryToServerMongoBuilder builder = new QueryToServerMongoBuilder("madcamp", "contacts");
                                QueryToServerMongo queryS = builder.getQueryD(new JSONArray().put(new BasicDBObject().append("contact.name", removed.getName())));
                                try {
                                    JSONObject obj = port.postToServerV2(queryS);
                                    if (obj.getString("result").equals("OK")){
                                        contacts.clear();
                                        obj = port.postToServerV2(builder.getQueryR(new JSONArray().put(new BasicDBObject())));
                                        if (obj.getString("result").equals("OK")) {
                                            if (obj.getJSONArray("data").length()>0){
                                                JSONArray found = obj.getJSONArray("data");
                                                List<Contact> contactList = new ArrayList<>();
                                                for (int i=0; i<found.length(); i++){
                                                    contactList.add(gson.fromJson(found.getJSONObject(i).getString("contact"), Contact.class));
                                                }
                                                System.out.println("ok");
                                                contacts.addAll(contactList);
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }
                                } catch(IOException e){
                                    e.printStackTrace();
                                } catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                //PostAdapter adapter = new PostAdapter(mContext, newjarray);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }
}