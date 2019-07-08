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
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class PtaxiAdapter  extends RecyclerView.Adapter<PtaxiViewHolder> {
    private Context mContext;
    private List<Taxitime> mTaxitime;
    PortToServer port;
    Gson gson = new Gson();

    public PtaxiAdapter(Context mContext, List<Taxitime> time) {
        this.mContext = mContext;
        this.mTaxitime = time;
        this.port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)mContext).cookies);
    }

    @NonNull
    @Override
    public PtaxiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.time_data,null);
        PtaxiViewHolder postViewHolder=new PtaxiViewHolder(baseView);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PtaxiViewHolder holder, final int position) {
        Taxitime taxitime= mTaxitime.get(position);
        holder.ivStime.setText(taxitime.stime);
        holder.ivEtime.setText(taxitime.etime);
        holder.ivlimit.setText(taxitime.limit);
        if(holder.ivpeople.getText()==null){
            holder.ivpeople.setText(0);
        }
        holder.ivtaxirow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm Message")
                        .setMessage("일정을 삭제하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mTaxitime.remove(position);
                                Toast.makeText(mContext.getApplicationContext(), "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                DBObject query = QueryBuilder.start("account._id").is("myhwang99").get();
                                System.out.println("123456");
                                System.out.println(query);
                                QueryToServerMongoBuilder builder = new QueryToServerMongoBuilder("madcamp", "taxi");
                                QueryToServerMongo queryS = builder.getQueryU(new JSONArray().put(query).put(QueryBuilder.start("$set").is(QueryBuilder.start("taxi").is(mTaxitime.toArray()).get()).get()));
                                try {
                                    JSONObject obj = port.postToServerV2(queryS);
                                    if (obj.getString("result").equals("OK")){
                                        mTaxitime.clear();
                                        obj = port.postToServerV2(builder.getQueryR(new JSONArray().put(new BasicDBObject())));
                                        if (obj.getString("result").equals("OK")) {
                                            if (obj.getJSONArray("data").length()>0){
                                                JSONObject found = (JSONObject)obj.getJSONArray("data").get(0);
                                                List<Taxitime> taxilist = (List<Taxitime>) gson.fromJson(found.getString("taxi"), new TypeToken<List<Taxitime>>(){}.getType());
                                                System.out.println("ok");
                                                mTaxitime.addAll(taxilist);
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
                return true;
            }
        });
        holder.iventer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인원수 추가
                CharSequence currentholder= holder.ivpeople.getText();
                int peoplenumber = Integer.parseInt(currentholder.toString());
                peoplenumber++;
                CharSequence added= (CharSequence)Integer.toString(peoplenumber);
                holder.ivpeople.setText(added);

                //알림 보내기

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaxitime.size();
    }



}
