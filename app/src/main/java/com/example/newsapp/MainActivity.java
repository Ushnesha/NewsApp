package com.example.newsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.newsapp.Adapters.NewsAdapter;
import com.example.newsapp.Adapters.NewsCategoryAdapter;
import com.example.newsapp.Fragments.NewsFragment;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Remote.UpdaterService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


//    public static final String ARRAYLISTKEY="arraylist";
//    public static final String FILTER_ACTION_KEY="any_key";
//    public ArrayList<NewsModel> newsList=new ArrayList<>();
//    public MyReceiver myReceiver;
    public static final String TAG="MainActivity";
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private NewsAdapter mNewsAdapter;
//
    private SearchView mSearchView;
    public static final String Activity_Query="query";
    public String searchString="all";
//    private boolean mIsRefreshing=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ViewPager viewPager=findViewById(R.id.viewpager);
        TabLayout tabLayout=findViewById(R.id.sliding_tabs);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this, android.R.color.black)
        );
        tabLayout.setupWithViewPager(viewPager);

        NewsCategoryAdapter newsCategoryAdapter=new NewsCategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(newsCategoryAdapter);


//        refresh();
//
//        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//                updateRefreshUI();
//            }
//        });
//
//        mNewsAdapter=new NewsAdapter(this,newsList);
//        RecyclerView recyclerView=findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(mNewsAdapter);
//
//        if(savedInstanceState==null){
//            refresh();
//        }


    }

    private void refresh(){
        Log.e(TAG,"onRefresh called");
        Intent intent=new Intent(this, UpdaterService.class);
        intent.putExtra(NewsFragment.Query,searchString);
        Log.e(TAG, searchString);
        startService(intent);
    }
//    public void updateRefreshUI(){
//        Log.e(TAG,"updateRefreshUI()");
//        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
//    }

//    private void setReceiver(){
//        Log.e(TAG,"setReceiver()");
//        myReceiver=new MyReceiver();
//        IntentFilter intentFilter= new IntentFilter();
//        intentFilter.addAction(FILTER_ACTION_KEY);
//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
//    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if( ! mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                searchString=s;
                refresh();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;

    }



//    private class MyReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if(FILTER_ACTION_KEY.equals(intent.getAction())){
//                mIsRefreshing=intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING,false);
//                newsList = intent.getParcelableArrayListExtra(ARRAYLISTKEY);
//                if(newsList == null)
//                    Log.e(TAG,"null newsList received");
//                else{
//                    Log.e(TAG,"newsList received");
//                    updateRefreshUI();
//                    updateAdapter();
//                }
//            }
//
//
//        }
//    }
//
//    public void updateAdapter(){
//
//        mNewsAdapter.clear();
//        mNewsAdapter.addAll(newsList);
//
//    }
//
//
//    @Override
//    protected void onStart() {
//        Log.e(TAG, "OnStart");
//        setReceiver();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        Log.e(TAG, "OnStop");
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
//        super.onStop();
//    }
}
