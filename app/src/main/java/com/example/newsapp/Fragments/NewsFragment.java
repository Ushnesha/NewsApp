package com.example.newsapp.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.Adapters.NewsAdapter;
import com.example.newsapp.MainActivity;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;
import com.example.newsapp.Remote.UpdaterService;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private static final String NEWS_FRAGMENT="Category";
    private String newsCategory;
    private NewsAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String TAG="NewsFragment";
    public static final String Query="query";
    public String searchString="all";
    private boolean mIsRefreshing=false;
    public ArrayList<NewsModel> newsList=new ArrayList<>();
    public static final String ARRAYLISTKEY="arraylist";
    public static final String FILTER_ACTION_KEY="any_key";
    public static final String REFRESH_ACTION_KEY="refresh_key";
    public MyReceiver myReceiver;

    public NewsFragment(){

    }

    public static NewsFragment newsInstance(String newsCategory){

        NewsFragment fragment= new NewsFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(NEWS_FRAGMENT, newsCategory);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            newsCategory=getArguments().getString(NEWS_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recycler, container, false);


        mSwipeRefreshLayout=(SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
        mSwipeRefreshLayout.setRefreshing(false);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                refresh();
                mSwipeRefreshLayout.setRefreshing(false);
                //updateRefreshUI();
            }
        });

        mNewsAdapter=new NewsAdapter(getContext(),newsList);
        RecyclerView recyclerView=rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mNewsAdapter);

        if(savedInstanceState==null){
            mSwipeRefreshLayout.setRefreshing(true);
            refresh();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        return  rootView;

    }

    public void refresh(){
        Log.e(TAG,"onRefresh from fragment called");
        Intent intent=new Intent(getActivity(), UpdaterService.class);
        intent.putExtra(Query,newsCategory);
        Log.e(TAG, searchString);
        getActivity().startService(intent);
    }

    public void updateRefreshUI(){
        Log.e(TAG,"updateRefreshUI()");
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    private void setReceiver(){
        Log.e(TAG,"setReceiver()");
        myReceiver=new MyReceiver();
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver, intentFilter);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(FILTER_ACTION_KEY.equals(intent.getAction())){
                newsList = intent.getParcelableArrayListExtra(ARRAYLISTKEY);
                if (newsList == null)
                    Log.e(TAG, "null newsList received");
                else {
                    Log.e(TAG, "newsList received");
                    updateAdapter();
                }
            }



        }
    }


    public void updateAdapter(){

        mNewsAdapter.clear();
        mNewsAdapter.addAll(newsList);

    }

    @Override
    public void onStart() {
        Log.e(TAG, "OnStart");
        setReceiver();
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "OnStop");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        super.onStop();
    }


}
