package com.mushroom.waylf.library;

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
}
