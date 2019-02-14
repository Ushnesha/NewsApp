package com.example.newsapp.HelperClasses;

import android.content.Context;
import android.util.Log;

import com.example.newsapp.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c, String url, SimpleDraweeView img)
    {
        if(url != null && url.length()>0)
        {
            Log.e("Imagrl", url);
            Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img);

        }else {
            Picasso.with(c).load(R.drawable.placeholder).into(img);
        }
    }

}

