package com.example.newsapp.Remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.newsapp.Models.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class EndpointUtils {

    public static final String TAG="EndpointUtils";

    private EndpointUtils(){}

    public static boolean isCheckConnection(Context context){
        Log.e(TAG,"isCheckConnection() called");
        boolean ch;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if(cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null || !ni.isConnected()) {
                Log.e(TAG, "Not online, not refreshing.");
                ch=false;
            }else{
                ch=true;
            }
        }else{
            Log.e(TAG, "cm is null");
            ch=false;
        }
        return ch;
    }

    static ArrayList<NewsModel> fetchNews(String url){

        Log.e(TAG, "fetchNews is called");

        if(TextUtils.isEmpty(url)){
            Log.e(TAG, "url null");
            return null;
        }

        Log.e(TAG, url);

        ArrayList<NewsModel> newsList=new ArrayList<>();
        try{
            JSONObject rawJsonObj= new JSONObject(url);
            JSONArray newsArray= rawJsonObj.getJSONArray("articles");
            for(int i=0;i<newsArray.length();i++){
                JSONObject news=newsArray.getJSONObject(i);
                JSONObject sec=news.getJSONObject("source");
                String newsId=sec.getString("id");
                String newsName=sec.getString("name");
                String newsAuthor=news.getString("author");
                String newstitle=news.getString("title");
                String newsdesc=news.getString("description");
                String newsUrl=news.getString("url");
                String newsImgUrl=news.getString("urlToImage");
                String newsPubTime=news.getString("publishedAt");
                String newsContent=news.getString("content");

                SimpleDateFormat rawDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US);
                Date newsDate;
                SimpleDateFormat readable = new SimpleDateFormat("EEE, MMM d, ''yy");

                String formattedDate = "";
                try{
                    newsDate=rawDateFormat.parse(newsPubTime);
                    formattedDate=readable.format(newsDate);
                } catch (ParseException e) {
                    Log.e(TAG,"Error parsing date");
                    e.printStackTrace();
                }
                Log.e(TAG, newsId+" "+newsName+" "+newsAuthor+" "+newsdesc+" "+newsUrl+" "+newsImgUrl+" "+formattedDate+" "+newsContent+" "+newstitle);
                newsList.add(new NewsModel(newsId,newsName,newsAuthor,newstitle,newsdesc,newsUrl,newsImgUrl,formattedDate,newsContent));

            }
        }catch (JSONException e) {
            Log.e(TAG,"Problem parsing the JSON news results");
            e.printStackTrace();
        }

        return newsList;
    }

    public static String fetchPlainText(URL url) throws IOException{
        Log.e(TAG,"fetchPlainText");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String urlString=response.body().string();
        Log.e(TAG,urlString);
        return urlString;
    }

}
