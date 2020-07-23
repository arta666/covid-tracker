package com.arman.covidtracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arman.covidtracker.model.Country;

@Database(entities = {Country.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract CountryDao countryDao();

}