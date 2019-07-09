package com.example.newfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class myregister extends Fragment {
    Context context;
    RecyclerView recyclerView;
    List<Taxitime> taxitime = new ArrayList<>();
    List<Taxitime> myregister = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ((MainActivity)getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.registerfrg, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        System.out.println("enter myregister");

        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServer queryS;
            BasicDBObject query = new BasicDBObject().append("account.name", ((MainActivity)getActivity()).account.getString("name"));
            queryS = new QueryToServerMongo("madcamp", "taxi_public", "/crud/research", new JSONArray().put(query));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONArray found = respond.getJSONArray("data");
                        List<Taxitime> newTaxi = new ArrayList<>();
                        for (int i = 0; i < found.length(); i++) {
                            try {
                                Taxitime taxi = gson.fromJson(found.getJSONObject(i).getString("taxi"), Taxitime.class);
                                newTaxi.add(taxi);
                            } catch (Exception e) {
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

        for (int i = 0; i < taxitime.size(); i++) {
            Taxitime taxiitem = taxitime.get(i);
            if (taxiitem.enter.equals("yes")) {
                myregister.add(taxiitem);
            }
        }

        RecyclerView recyclerView;
        recyclerView = rootView.findViewById(R.id.show_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final PtaxiAdapter adapter = new PtaxiAdapter(context, myregister){

            @Override
            public void onBindViewHolder(@NonNull final PtaxiViewHolder holder, final int position) {
                Taxitime taxitime= getmTaxitime().get(position);
                holder.ivStime.setText(taxitime.stime);
                holder.ivEtime.setText(taxitime.etime);
                holder.ivlimit.setText(taxitime.limit);
                if(taxitime.enter.equals("yes")){
                    holder.iventer.setText("나가기");
                    holder.iventer.setBackground(getmContext().getDrawable(R.drawable.layout_bgc));
                }

                if(holder.ivpeople.getText()==null){
                    System.out.println("this is now be filled");
                    holder.ivpeople.setText('0');
                }
/*
                holder.ivtaxirow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Confirm Message")
                                .setMessage("일정을 삭제하시겠습니까?")
                                .setIcon(android.R.drawable.ic_menu_save)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Taxitime removed = mTaxitime.remove(position);
                                        Toast.makeText(mContext.getApplicationContext(), "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                        QueryToServerMongoBuilder builder = new QueryToServerMongoBuilder("madcamp", "taxi_public");
                                        QueryToServerMongo queryS = builder.getQueryD(new JSONArray().put(new BasicDBObject().append("taxi.user", removed.user)));
                                        try {
                                            JSONObject obj = port.postToServerV2(queryS);
                                            if (obj.getString("result").equals("OK")){
                                                mTaxitime.clear();
                                                obj = port.postToServerV2(builder.getQueryR(new JSONArray().put(new BasicDBObject())));
                                                if (obj.getString("result").equals("OK")) {
                                                    if (obj.getJSONArray("data").length()>0){
                                                        JSONArray found = obj.getJSONArray("data");
                                                        List<Taxitime> taxitimeList = new ArrayList<>();
                                                        for (int i=0; i<found.length(); i++){
                                                            taxitimeList.add(gson.fromJson(found.getJSONObject(i).getString("taxi"), Taxitime.class));
                                                        }
                                                        System.out.println("ok");
                                                        mTaxitime.addAll(taxitimeList);
                                                    }
                                                }
                                                notifyDataSetChanged();
                                                PtaxiAdapter po = new PtaxiAdapter(mContext,mTaxitime);
                                                po.notifyDataSetChanged();
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
                        return true;
                    }
                });
*/
                holder.iventer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //인원수 추가
                        CharSequence currentholder= holder.ivpeople.getText();
                        CharSequence currentlimit = holder.ivlimit.getText();
                        final int peoplenumber = Integer.parseInt(currentholder.toString());
                        int limitnum = Integer.parseInt(currentlimit.toString());
                        if(holder.iventer.getText().equals("참여")) {
                            if (peoplenumber >= limitnum) {
                                Toast.makeText(getmContext(),"정원초과!",Toast.LENGTH_SHORT).show();
                            } else {
                                int innum = peoplenumber + 1;
                                CharSequence added = (CharSequence) Integer.toString(innum);
                                holder.ivpeople.setText(added);
                                holder.iventer.setText("나가기");
                                holder.iventer.setBackground(getmContext().getDrawable(R.drawable.layout_bgc));


                            }
                        }
                        else {
                            int denum = peoplenumber - 1;
                            CharSequence added = (CharSequence) Integer.toString(denum);
                            holder.ivpeople.setText(added);
                            holder.iventer.setText("참여");
                            holder.iventer.setBackground(getmContext().getDrawable(R.drawable.layout_bg));
                        }
                    }
                });

                holder.ivtaxirow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            System.out.println("clicked");
                            Chat chat = new Chat();
                            Bundle bundle = new Bundle(2);
                            bundle.putString("name", ((MainActivity) getActivity()).account.getString("name"));
                            Taxitime chatroom = getmTaxitime().get(position);
                            bundle.putString("room", chatroom.user);
                            chat.setArguments(bundle);
                            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, chat).commit();

                        } catch (Exception e){

                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}

