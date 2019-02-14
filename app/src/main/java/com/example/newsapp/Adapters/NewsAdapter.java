package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.HelperClasses.PicassoClient;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsModel> mNewsList;
    private Context context;

    public NewsAdapter(Context context, ArrayList<NewsModel> list){
        mNewsList=list;
        this.context= context;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.news_card, parent, false);

        final NewsViewHolder vh=new NewsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NewsModel nm=getNews(vh.getAdapterPosition());
                String url=nm.getUrl();
                Uri uri=Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }



        });

        return vh;
    }

    private NewsModel getNews(int position){
        return mNewsList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        final NewsModel currentItem = mNewsList.get(position);
        if(currentItem.getTitle()!=null)
        holder.news_title.setText(currentItem.getTitle());
        else
            holder.news_title.setText("");
        holder.news_time.setText(currentItem.getPubAt());
        if(currentItem.getName()!=null)
        holder.news_author.setText(currentItem.getName());
        else
            holder.news_author.setText("");
        PicassoClient.downloadImage(context, currentItem.getImgUrl(),holder.thumbnail);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getUrl());
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);
                //Toast.makeText(context, "share_item is clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView thumbnail;
        TextView news_title;
        TextView news_author;
        TextView news_time;
        ImageView img;

        public NewsViewHolder(View itemView) {
            super(itemView);

            thumbnail = (SimpleDraweeView) itemView.findViewById(R.id.thumbnail);
            news_title = (TextView) itemView.findViewById(R.id.news_headline);
            news_author = (TextView) itemView.findViewById(R.id.news_author);
            news_time = (TextView) itemView.findViewById(R.id.news_time);
            img=(ImageView) itemView.findViewById(R.id.share_view);
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Intent sendIntent = new Intent();
////                    sendIntent.setAction(Intent.ACTION_SEND);
////                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
////                    sendIntent.setType("text/plain");
////                    context.startActivity(sendIntent);
//                    Toast.makeText(context, "share_item is clicked", Toast.LENGTH_SHORT).show();
//
//                }
//            });

        }
    }

    public void clear(){

        mNewsList=new ArrayList<>();

    }

    public ArrayList<NewsModel> filter(String text){
        text=text.toLowerCase(Locale.getDefault());
        ArrayList<NewsModel> searchedNews=new ArrayList<>();
            for(NewsModel nm : mNewsList){
                for(int i=0;i< nm.getDesc().length();i++){
                    if(nm.getTitle().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getContent().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getAuthor().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getName().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getDesc().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getImgUrl().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getUrl().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getPubAt().toLowerCase(Locale.getDefault()).contains(text)||
                            nm.getId().toLowerCase(Locale.getDefault()).contains(text)){

                        searchedNews.add(nm);

                    }
                }
            }
            mNewsList=searchedNews;

        notifyDataSetChanged();
        return searchedNews;
    }

    public void addAll(ArrayList<NewsModel> news){

        for(int i=0;i<news.size();i++){
            NewsModel newsSingle=news.get(i);
            mNewsList.add(newsSingle);
            notifyDataSetChanged();
        }

    }
}
