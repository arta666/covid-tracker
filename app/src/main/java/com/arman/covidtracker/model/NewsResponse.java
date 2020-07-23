package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse implements Parcelable {

    @Expose
    @SerializedName("total")
    private long total;

    @Expose
    @SerializedName("items")
    private List<News> items;

    public NewsResponse() {
    }

    protected NewsResponse(Parcel in) {
        total = in.readLong();
        items = in.createTypedArrayList(News.CREATOR);
    }

    public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel in) {
            return new NewsResponse(in);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<News> getItems() {
        return items;
    }

    public void setItems(List<News> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(total);
        dest.writeTypedList(items);
    }
}
