package com.example.newfinal;

import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class data {

    public static String str = "[{'name':'NAME','number':'NUMBER'}," + "{'name':'mom','number':'010-2322-2321'}," + "{'name':'dad','number':'010-1111-2222'}," + "{'name':'kim','number':'010-1111-2222'}," + "{'name':'lee','number':'010-1234-5678'}]";

    public static JSONArray newJSON= makedata();
    public static  JSONArray makedata() {
        try {
            newJSON = new JSONArray(str);
            System.out.println(newJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newJSON;
    }
public static String menulist[]= {"삼겹살","pizza","ramen","rice","sushi","hamburger","pasta","돈까스","chicken","파전"};

    public static ArrayList<String> menu= new ArrayList<String>(Arrays.asList("삼겹살","pizza","ramen","rice","sushi","hamburger","pasta","돈까스","chicken","파전"));
    public static ArrayList<Integer> myList = new ArrayList<Integer>(Arrays.asList(R.drawable.meat,R.drawable.pizza,R.drawable.ramen,R.drawable.rice,R.drawable.sushi,R.drawable.hamburger,R.drawable.pasta,R.drawable.pork,R.drawable.chicken,R.drawable.pajeon));
    public static int[] imgs ={R.drawable.meat, R.drawable.pizza,R.drawable.ramen,R.drawable.rice,R.drawable.sushi,R.drawable.hamburger,R.drawable.pasta,R.drawable.pork,R.drawable.chicken,R.drawable.pajeon};
    public static boolean[] checkedItems = {true,true,true,true,true,true,true,true,true,true};

    public static ArrayList<Uri> images = new ArrayList<>();
}
