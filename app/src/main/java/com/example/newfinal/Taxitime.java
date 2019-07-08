package com.example.newfinal;

import com.google.gson.Gson;

public class Taxitime {

    String user;
    String time;
    String limit;

    public String get_user() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public void setUser(String name) {
        this.user = user;
    }


    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Taxitime(String user, String time, String limit) {
        this.user = user;
        this.time = time;
        this.limit = limit;
    }

    public String toString (){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}