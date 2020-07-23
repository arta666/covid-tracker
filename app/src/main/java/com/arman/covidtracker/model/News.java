package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News implements Parcelable {

    @Expose
    @PrimaryKey
    @SerializedName("nid")
    private long id;

    @Expose
    @SerializedName("title")
    private String title ;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("author")
    private String author;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("urlToImage")
    private String urlToImage;

    @Expose
    @SerializedName("publishedAt")
    private String publishedAt;

    @Expose
    @SerializedName("addedOn")
    private String addedOn;

    @Expose
    @SerializedName("siteName")
    private String siteName;

    @Expose
    @SerializedName("language")
    private String language;

    @Expose
    @SerializedName("countryCode")
    private String countryCode;

    @Expose
    @SerializedName("status")
    private int status;

    public News() {
    }

    protected News(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        author = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        addedOn = in.readString();
        siteName = in.readString();
        language = in.readString();
        countryCode = in.readString();
        status = in.readInt();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(addedOn);
        dest.writeString(siteName);
        dest.writeString(language);
        dest.writeString(countryCode);
        dest.writeInt(status);
    }
}
