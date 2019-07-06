package com.example.newfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    PortToServer port = new PortToServer("http://143.248.36.38:3000");
    QueryToServerMongoBuilder builderContacts = new QueryToServerMongoBuilder("madcamp", "contacts");
    QueryToServerMongoBuilder builderGalleries = new QueryToServerMongoBuilder("madcamp", "galleries");
    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();
    Fragment3 fragment3 = new Fragment3();
    Login_fragment loginFragment = new Login_fragment();
    MainFragment mainFragment = new MainFragment();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, loginFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("activity result");
        System.out.println("request: "+requestCode);
        System.out.println("result:"+ resultCode);
        //System.out.println(data.getStringArrayExtra("item")[0]);
        switch (requestCode-65536) {
            case 1: {
                if (true) {
                    //연락처 등록
                    System.out.println("result ok");
                    String[] item = data.getStringArrayExtra("item");
                    Contact contact = new Contact(item[0], item[1], item[2]);
                    try {
                        port.postToServerV2(builderContacts.getQueryU(new JSONArray().put(QueryBuilder.start("account._id").is("myhwang99").get()).put(QueryBuilder.start("$push").is(QueryBuilder.start("contacts").is((BasicDBObject) JSON.parse(contact.toString())).get()).get())));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    //fragment1.onAttach(this);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new Fragment1()).commitAllowingStateLoss();
                }
                break;
            }
            case 2: {
                if (resultCode == RESULT_OK) {
                }
                break;
            }
        }
    }
}