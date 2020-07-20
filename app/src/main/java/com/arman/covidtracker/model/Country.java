package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

class Country implements Parcelable {
    private String Country;
    private String CountryCode;
    private String Slug;
    private int NewConfirmed;
    private long TotalConfirmed;
    private int NewDeaths;
    private int TotalDeaths;
    private int NewRecovered;
    private long TotalRecovered;
    private String Date;

    protected Country(Parcel in) {
        Country = in.readString();
        CountryCode = in.readString();
        Slug = in.readString();
        NewConfirmed = in.readInt();
        TotalConfirmed = in.readLong();
        NewDeaths = in.readInt();
        TotalDeaths = in.readInt();
        NewRecovered = in.readInt();
        TotalRecovered = in.readLong();
        Date = in.readString();
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
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public long getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        NewDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public int getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        NewRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Country);
        dest.writeString(CountryCode);
        dest.writeString(Slug);
        dest.writeInt(NewConfirmed);
        dest.writeLong(TotalConfirmed);
        dest.writeInt(NewDeaths);
        dest.writeInt(TotalDeaths);
        dest.writeInt(NewRecovered);
        dest.writeLong(TotalRecovered);
        dest.writeString(Date);
    }
}
