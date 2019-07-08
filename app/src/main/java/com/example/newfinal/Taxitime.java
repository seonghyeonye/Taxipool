package com.example.newfinal;

import com.google.gson.Gson;

public class Taxitime {

    String user;
    String stime;
    String etime;
    String limit;
    String date;
    String startplace;
    String endplace;
    String enter;

    public String get_user() {
        return user;
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

    public Taxitime(String user, String stime, String etime, String limit, String date, String startplace, String endplace, String enter) {
        this.user = user;
        this.stime = stime;
        this.etime=etime;
        this.limit = limit;
        this.date= date;
        this.startplace= startplace;
        this.endplace=endplace;
        this.enter=enter;
    }

    public String toString (){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}