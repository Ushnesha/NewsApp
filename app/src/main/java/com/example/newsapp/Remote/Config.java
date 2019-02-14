package com.example.newsapp.Remote;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {

    private static final String TAG="Config";
    public static final URL NEWS_ALL_URL;



    static{
        URL url=null;
        try{
            url = new URL("https://newsapi.org/v2/everything?q=%1$s&sortBy=publishedAt&apiKey=8800a4b6343c40029809370302c481fc");
        }catch (MalformedURLException m){

            Log.e(TAG, "Please check your internet connection");
        }
        NEWS_ALL_URL=url;
    }

    public static URL createUrlFromString(String s){
        String requestUrl="https://newsapi.org/v2/everything?q=%1$s&sortBy=publishedAt&apiKey=8800a4b6343c40029809370302c481fc";
        String fullUrl=String.format(requestUrl,s);
        URL url=null;
        try{
            url = new URL(fullUrl);
        }catch (MalformedURLException m){

            Log.e(TAG, "Please check your internet connection");
        }
        return url;
    }


}
