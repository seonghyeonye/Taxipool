package com.example.newfinal;

import org.json.JSONArray;

public class QueryToServerMongo extends QueryToServer {
    String dbName;
    String colName;
    JSONArray queries;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public JSONArray getQueries() {
        return queries;
    }

    public void setQueries(JSONArray queries) {
        this.queries = queries;
    }

    public QueryToServerMongo(String dbName, String colName, String target, JSONArray queries) {
        this.dbName = dbName;
        this.colName = colName;
        this.target = target;
        this.queries = queries;
    }
}
