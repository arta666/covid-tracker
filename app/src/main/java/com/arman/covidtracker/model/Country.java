package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {

    @Expose
    @SerializedName("Country")
    private String country;

    @Expose
    @SerializedName("CountryCode")
    private String countryCode;

    @Expose
    @SerializedName("Slug")
    private String slug;

    @Expose
    @SerializedName("NewConfirmed")
    private long newConfirmed;

    @Expose
    @SerializedName("TotalConfirmed")
    private long totalConfirmed;

    @Expose
    @SerializedName("NewDeaths")
    private long newDeaths;

    @Expose
    @SerializedName("TotalDeaths")
    private long totalDeaths;

    @Expose
    @SerializedName("NewRecovered")
    private long newRecovered;

    @Expose
    @SerializedName("TotalRecovered")
    private long totalRecovered;

    @Expose
    @SerializedName("Date")
    private String date;

    protected Country(Parcel in) {
        country = in.readString();
        countryCode = in.readString();
        slug = in.readString();
        newConfirmed = in.readInt();
        totalConfirmed = in.readLong();
        newDeaths = in.readInt();
        totalDeaths = in.readInt();
        newRecovered = in.readInt();
        totalRecovered = in.readLong();
        date = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
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

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(int newRecovered) {
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
        dest.writeString(country);
        dest.writeString(countryCode);
        dest.writeString(slug);
        dest.writeLong(newConfirmed);
        dest.writeLong(totalConfirmed);
        dest.writeLong(newDeaths);
        dest.writeLong(totalDeaths);
        dest.writeLong(newRecovered);
        dest.writeLong(totalRecovered);
        dest.writeString(date);
    }
}
