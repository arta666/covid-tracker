package com.arman.covidtracker.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.arman.covidtracker.model.Country;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM countries")
    LiveData<List<Country>> getCountriesLiveData();


    @Query("SELECT * FROM countries")
    List<Country> getCountries();


    @Insert(onConflict = REPLACE)
    void insert(Country country);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Country> countries);

    @Query("DELETE FROM countries")
    void deleteAll();
}