package com.example.newfinal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.internal.Validate;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**

 */
public class Chat extends Fragment {
    private String TAG = "MainActivity";
    private io.socket.client.Socket mSocket;
    private Button sendButton;
    private EditText textArea;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private static List<JSONObject> chats = new ArrayList<>();
    private ChatAdapter adapter;
    private String room;
    private String name;
    private PortToServer port;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        room = getArguments().getString("room");
        name = getArguments().getString("name");
        mainActivity = (MainActivity)getActivity();
        try {
            mSocket = IO.socket("http://143.248.36.38:80");
            mSocket.connect();
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("chat message", onMessageReceived);
            /*
            mSocket.on("login", onNewMember);
            mSocket.on("logout", onMemberQuit);
            */
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chat_layout, container, false);
        recyclerView = view.findViewById(R.id.reyclerviewMesssageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        adapter = new ChatAdapter(mainActivity, chats);
        chats.clear();
        recyclerView.setAdapter(adapter);
        /*
        try {
            PortToServer port = new PortToServer("http://143.248.36.38:3000", ((MainActivity)getActivity()).cookies);
            QueryToServer queryS;
            /*
            BasicDBObject query = new BasicDBObject().append("account._id", "myhwang99");
            JSONArray queries = new JSONArray().put(query);*//*
            queryS = new QueryToServerMongo("madcamp", "contacts", "/crud/research", new JSONArray().put(new BasicDBObject()));
            JSONObject respond = port.postToServerV2(queryS);
            if (respond!=null) {
                if (respond.getString("result").equals("OK")) {
                    if (respond.getJSONArray("data").length() > 0) {
                        Gson gson = new Gson();
                        JSONArray found = respond.getJSONArray("data");
                        List<Contact> contactList = new ArrayList<>();
                        for (int i=0; i<found.length(); i++){
                            try {
                                contactList.add(gson.fromJson(found.getJSONObject(i).getString("contact"), Contact.class));
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        System.out.println("ok");
                        contacts.addAll(contactList);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        adapter.notifyDataSetChanged();
        textArea = view.findViewById(R.id.textArea);
        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textArea.getText().toString();
                mSocket.emit("say", msg);
                textArea.setText("");
                JSONObject obj = new JSONObject();
                try {
                    obj.put("profile", "unknown").put("name", "unknown").put("body", msg);
                }catch(Exception e){
                    e.printStackTrace();
                }
                chats.add(obj);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    // Socket서버에 connect 되면 발생하는 이벤트
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                System.out.println("!1231312");
                mSocket.emit("init", new JSONObject().put("room", room).put("name", name));
                System.out.println("fefe");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    // 서버로부터 전달받은 'chat-message' Event 처리.
    Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject msg = (JSONObject)args[0];
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("falekjfe");
                        JSONObject obj = new JSONObject();
                        obj.put("profile", "unknown").put("name", msg.getString("talker")).put("body", msg.getString("talk"));
                        chats.add(obj);
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, msg.getString("talk"));
                        ;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMember = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.

            try {

                JSONObject receivedData = (JSONObject) args[0];
                Log.d(TAG, receivedData.getString("msg"));
                Log.d(TAG, receivedData.getString("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onMemberQuit = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.
            try {
                JSONObject receivedData = (JSONObject) args[0];
                Log.d(TAG, receivedData.getString("msg"));
                Log.d(TAG, receivedData.getString("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
