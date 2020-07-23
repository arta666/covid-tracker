package com.arman.covidtracker.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.arman.covidtracker.model.Country;

import java.util.List;


public interface CountryRepository {


    LiveData<List<Country>> getCountriesLiveData();


    List<Country> getCountries();


    void insert(Country country);


    void insertAll(List<Country> countries);

    void deleteAll();
}
