package com.example.luchi.nytimessearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luchi on 2/13/2018.
 */

@Parcel
public class Article  {
String webUrl;
String headline;
String thumbnail;


    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public static ArrayList<Article> fromJSOnArray(JSONArray array){
    ArrayList<Article> articles = new ArrayList<>();
    for(int x = 0 ; x < array.length(); x ++)
    {
        try {
            articles.add(new Article(array.getJSONObject(x)));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
    return articles;
}
public Article(){

}
    public Article(JSONObject jsonObject){
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
        JSONArray multimedia = jsonObject.getJSONArray("multimedia");
        if(multimedia.length() > 0)
        {
            JSONObject multimediaJson = multimedia.getJSONObject(0);
            this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
        }
        else {
            this.thumbnail = "";
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
