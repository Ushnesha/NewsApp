package com.example.newsapp.Remote;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapp.MainActivity;
import com.example.newsapp.Models.NewsModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class UpdaterService extends IntentService {

    public static final String TAG="UpdaterService";
    //public static final String BROADCAST_ACTION_STATE_CHANGE="com.example.newsapp.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING="com.example.newsapp.intent.extra.REFRESHING";

    public UpdaterService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.e(TAG,"onHandleService called");
        String s=intent.getStringExtra(MainActivity.Query);

        if(EndpointUtils.isCheckConnection(this)){
           sendBroadcast(new Intent(MainActivity.FILTER_ACTION_KEY).putExtra(EXTRA_REFRESHING, true));


            try {
                URL fetchedUrl=Config.createUrlFromString(s);
                String urlString= EndpointUtils.fetchPlainText(fetchedUrl);
                ArrayList<NewsModel> newsList= EndpointUtils.fetchNews(urlString);
                Intent intent1=new Intent();
                intent1.setAction(MainActivity.FILTER_ACTION_KEY);
                intent1.putExtra(EXTRA_REFRESHING, false);
                intent1.putParcelableArrayListExtra(MainActivity.ARRAYLISTKEY, newsList);

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

            } catch (IOException e) {
                Log.e(TAG, "Unable to fetch plain text");
                e.printStackTrace();
            }

            sendBroadcast(new Intent(MainActivity.FILTER_ACTION_KEY).putExtra(EXTRA_REFRESHING,false));

        }else {
            Handler handler=new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                @Override
                public void run() {
                    Toast.makeText(UpdaterService.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



}
