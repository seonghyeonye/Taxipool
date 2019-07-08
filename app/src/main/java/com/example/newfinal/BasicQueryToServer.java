package com.example.newfinal;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicQueryToServer extends QueryToServer {
    JSONObject data;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public BasicQueryToServer setData(BasicDBObject object){
        try {
            this.data = new JSONObject(JSON.serialize(object));
            return this;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public BasicQueryToServer(String target) {
        super(target);
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
