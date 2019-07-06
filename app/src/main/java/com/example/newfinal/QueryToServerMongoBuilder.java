package com.example.newfinal;

import com.mongodb.DBObject;

import org.json.JSONArray;

import java.lang.reflect.Type;

public class QueryToServerMongoBuilder {
    String dbName;
    String colName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public QueryToServerMongoBuilder(String dbName, String colName) {
        this.dbName = dbName;
        this.colName = colName;
    }

    public QueryToServerMongo get(String target, JSONArray queries){
        QueryToServerMongo res= new QueryToServerMongo(getDbName(), getColName(), target, queries);
        return res;
    }

    public QueryToServerMongo getQueryC(JSONArray queries){
        return get("/crud/create", queries);
    }

    public QueryToServerMongo getQueryR(JSONArray queries){
        return get("/crud/research", queries);
    }

    public QueryToServerMongo getQueryU(JSONArray queries){
        return get("/crud/update", queries);
    }

    public QueryToServerMongo getQueryD(JSONArray queries){
        return get("/crud/delete", queries);
    }

}
