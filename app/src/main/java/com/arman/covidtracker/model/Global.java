package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Global implements Parcelable {

    @Expose
    @SerializedName("totalNewCases")
    private long newConfirmed;

    @Expose
    @SerializedName("totalConfirmed")
    private long totalConfirmed;

    @Expose
    @SerializedName("totalNewDeaths")
    private long newDeaths;

    @Expose
    @SerializedName("totalDeaths")
    private long totalDeaths;

    @Expose
    @SerializedName("NewRecovered")
    private long newRecovered;

    @Expose
    @SerializedName("totalRecovered")
    private long totalRecovered;

    @Expose
    @SerializedName("created")
    private String date;


    protected Global(Parcel in) {
        newConfirmed = in.readLong();
        totalConfirmed = in.readLong();
        newDeaths = in.readLong();
        totalDeaths = in.readLong();
        newRecovered = in.readLong();
        totalRecovered = in.readLong();
        date = in.readString();
    }

    public static final Creator<Global> CREATOR = new Creator<Global>() {
        @Override
        public Global createFromParcel(Parcel in) {
            return new Global(in);
        }

        @Override
        public Global[] newArray(int size) {
            return new Global[size];
        }
    };

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(long newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(long newRecovered) {
        this.newRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(newConfirmed);
        dest.writeLong(totalConfirmed);
        dest.writeLong(newDeaths);
        dest.writeLong(totalDeaths);
        dest.writeLong(newRecovered);
        dest.writeLong(totalRecovered);
        dest.writeString(date);
    }
}
