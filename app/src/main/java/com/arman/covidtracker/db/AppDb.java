package com.arman.covidtracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.News;

@Database(entities = {Country.class, News.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract CountryDao countryDao();

    public abstract NewsDao newsDao();

}