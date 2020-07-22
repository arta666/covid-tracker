package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {


    @Expose
    @SerializedName("countryName")
    private String country;

    @Expose
    @SerializedName("countryCode")
    private String countryCode;

    @Expose
    @SerializedName("lat")
    private double lat;

    @Expose
    @SerializedName("lng")
    private double lng;


    @Expose
    @SerializedName("NewConfirmed")
    private long newConfirmed;

    @Expose
    @SerializedName("confirmed")
    private long totalConfirmed;

    @Expose
    @SerializedName("NewDeaths")
    private long newDeaths;

    @Expose
    @SerializedName("deaths")
    private long totalDeaths;

    @Expose
    @SerializedName("NewRecovered")
    private long newRecovered;

    @Expose
    @SerializedName("recovered")
    private long totalRecovered;

    @Expose
    @SerializedName("Date")
    private String date;


    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public long getTotalConfirmed() {
        return totalConfirmed;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public static Creator<Country> getCREATOR() {
        return CREATOR;
    }

    protected Country(Parcel in) {
        country = in.readString();
        countryCode = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        newConfirmed = in.readLong();
        totalConfirmed = in.readLong();
        newDeaths = in.readLong();
        totalDeaths = in.readLong();
        newRecovered = in.readLong();
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Country)) {
            return false;
        }

        return country.equalsIgnoreCase(((Country) obj).getCountry());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.country != null ? this.country.hashCode() : 0);
        return hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(countryCode);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeLong(newConfirmed);
        dest.writeLong(totalConfirmed);
        dest.writeLong(newDeaths);
        dest.writeLong(totalDeaths);
        dest.writeLong(newRecovered);
        dest.writeLong(totalRecovered);
        dest.writeString(date);
    }
}
