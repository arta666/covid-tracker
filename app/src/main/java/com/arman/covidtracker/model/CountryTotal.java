package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryTotal implements Parcelable {

    @Expose
    @SerializedName("Country")
    private String country;

    @Expose
    @SerializedName("CountryCode")
    private String countryCode;

    @Expose
    @SerializedName("Province")
    private String province;

    @Expose
    @SerializedName("City")
    private String city;

    @Expose
    @SerializedName("CityCode")
    private String cityCode;

    @Expose
    @SerializedName("Lat")
    private String lat;

    @Expose
    @SerializedName("Lon")
    private String lon;

    @Expose
    @SerializedName("Confirmed")
    private long confirmed;

    @Expose
    @SerializedName("Deaths")
    private long deaths;

    @Expose
    @SerializedName("Recovered")
    private long recovered;

    @Expose
    @SerializedName("Active")
    private long active;

    @Expose
    @SerializedName("Date")
    private String date;

    protected CountryTotal(Parcel in) {
        country = in.readString();
        countryCode = in.readString();
        province = in.readString();
        city = in.readString();
        cityCode = in.readString();
        lat = in.readString();
        lon = in.readString();
        confirmed = in.readLong();
        deaths = in.readLong();
        recovered = in.readLong();
        active = in.readLong();
        date = in.readString();
    }

    public static final Creator<CountryTotal> CREATOR = new Creator<CountryTotal>() {
        @Override
        public CountryTotal createFromParcel(Parcel in) {
            return new CountryTotal(in);
        }

        @Override
        public CountryTotal[] newArray(int size) {
            return new CountryTotal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(countryCode);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(cityCode);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeLong(confirmed);
        dest.writeLong(deaths);
        dest.writeLong(recovered);
        dest.writeLong(active);
        dest.writeString(date);
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getActive() {
        return active;
    }

    public String getDate() {
        return date;
    }
}
