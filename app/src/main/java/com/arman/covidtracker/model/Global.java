package com.arman.covidtracker.model;

import android.os.Parcel;
import android.os.Parcelable;

class Global implements Parcelable {

    private long NewConfirmed ;
    private long TotalConfirmed;
    private long NewDeaths;
    private long TotalDeaths;
    private long NewRecovered;
    private long TotalRecovered;

    protected Global(Parcel in) {
        NewConfirmed = in.readLong();
        TotalConfirmed = in.readLong();
        NewDeaths = in.readLong();
        TotalDeaths = in.readLong();
        NewRecovered = in.readLong();
        TotalRecovered = in.readLong();
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
        return NewConfirmed;
    }

    public void setNewConfirmed(long newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public long getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(long totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public long getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        NewDeaths = newDeaths;
    }

    public long getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(long newRecovered) {
        NewRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(NewConfirmed);
        dest.writeLong(TotalConfirmed);
        dest.writeLong(NewDeaths);
        dest.writeLong(TotalDeaths);
        dest.writeLong(NewRecovered);
        dest.writeLong(TotalRecovered);
    }
}
