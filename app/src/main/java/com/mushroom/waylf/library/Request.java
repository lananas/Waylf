package com.mushroom.waylf.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Mushroom on 01/02/2015.
 */
public class Request {
    private String query = null;
    private String omdbapiUrl = "http://www.omdbapi.com/?";

    public String SearchListRequest(String nameMovie){
        query = "s=" + nameMovie + "&y=&plot=full&r=json";
        return omdbapiUrl + query;
    }
    public String SearchMovieRequest(String nameMovie){
        query = "t=" + nameMovie + "&y=&plot=full&r=json";
        return omdbapiUrl + query;
    }
    public String SearchIdRequest(String nameMovie){
        query = "i=" + nameMovie + "&y=&plot=full&r=json";
        return omdbapiUrl + query;
    }

    public JSONObject ReadJsonMovie(String JsonMovie) throws JSONException {
        JSONObject json = null;
        try {
            json = (JSONObject)new JSONParser().parse(JsonMovie);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    public List<String> ReadJsonList(String JsonList) throws JSONException {
        JSONObject obj = new JSONObject(JsonList);
        List<String> list = new ArrayList<String>();
        JSONArray array = obj.getJSONArray("Search");
        for(int i = 0 ; i < array.length() ; i++){
            list.add(array.getJSONObject(i).getString("Title")+";"+array.getJSONObject(i).getString("Year") +";" + array.getJSONObject(i).getString("imdbID"));
        }
        return list;
    }



}
