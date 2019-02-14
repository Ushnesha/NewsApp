package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.newsapp.Fragments.NewsFragment;
import com.example.newsapp.Remote.Config;

public class NewsCategoryAdapter extends FragmentPagerAdapter {

    private Resources resources;

    public NewsCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        resources=context.getResources();
    }

    @Override
    public Fragment getItem(int i) {

        String category;

        Fragment fragment;

        switch (i){

            case 0:
                category= Config.ALL;
                fragment= NewsFragment.newsInstance(category);
                return fragment;
            case 1:
                category=Config.BUSINESS;
                fragment=NewsFragment.newsInstance(category);
                return fragment;
            case 2:
                category=Config.TECH;
                fragment=NewsFragment.newsInstance(category);
                return fragment;
            case 3:
                category=Config.POLITICS;
                fragment=NewsFragment.newsInstance(category);
                return fragment;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return Config.ALL;
            case 1:
                return Config.BUSINESS;
            case 2:
                return Config.TECH;
            case 3:
                return Config.POLITICS;
            default:
                    return null;
        }
    }
}
