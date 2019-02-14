package com.example.newsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NewsModel implements Parcelable {

    private String id, name, author, title, desc, url, imgUrl, pubAt, content;

    public NewsModel(String id, String name, String author, String title, String desc, String url, String imgUrl, String pubTime, String content){
        this.id=id;
        this.name=name;
        this.author=author;
        this.title=title;
        this.desc=desc;
        this.url=url;
        this.imgUrl=imgUrl;
        this.pubAt=pubTime;
        this.content=content;
    }

    protected NewsModel(Parcel in){

        id=in.readString();
        name=in.readString();
        author=in.readString();
        title=in.readString();
        desc=in.readString();
        url=in.readString();
        imgUrl=in.readString();
        pubAt=in.readString();
        content=in.readString();

    }

    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPubAt() {
        return pubAt;
    }

    public void setPubAt(String pubAt) {
        this.pubAt = pubAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(url);
        parcel.writeString(imgUrl);
        parcel.writeString(pubAt);
        parcel.writeString(content);


    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

}
