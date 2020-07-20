package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Summary implements Parcelable {

    @Expose
    @SerializedName("Global")
    private Global global;

    @Expose
    @SerializedName("Countries")
    private List<Country> countries;

    @Expose
    @SerializedName("Date")
    private String date ;


    protected Summary(Parcel in) {
        global = in.readParcelable(Global.class.getClassLoader());
        countries = in.createTypedArrayList(Country.CREATOR);
        date = in.readString();
    }

    public static final Creator<Summary> CREATOR = new Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel in) {
            return new Summary(in);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
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
        dest.writeParcelable(global, flags);
        dest.writeTypedList(countries);
        dest.writeString(date);
    }

    @Override
    public String toString() {
        return "Summary{" +
                "date='" + date + '\'' +
                '}';
    }
}
