package com.example.newfinal;

import com.google.gson.Gson;
import com.mongodb.util.JSON;

import org.json.JSONArray;

public class QueryToServer {
    String target;

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public QueryToServer(String target) {
        this.target = target;
    }

    public QueryToServer() {
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
