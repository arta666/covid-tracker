package com.arman.covidtracker.repository;

import androidx.lifecycle.LiveData;

import com.arman.covidtracker.db.CountryDao;
import com.arman.covidtracker.model.Country;

import java.util.List;

import javax.inject.Inject;

public class CountryDataSource implements CountryRepository {

    private CountryDao countryDao;

    @Inject
    public CountryDataSource(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public LiveData<List<Country>> getCountriesLiveData() {
        return countryDao.getCountriesLiveData();
    }

    @Override
    public List<Country> getCountries() {
        return countryDao.getCountries();
    }

    @Override
    public void insert(Country country) {
        countryDao.insert(country);
    }

    @Override
    public void insertAll(List<Country> countries) {
        countryDao.insertAll(countries);
    }

    @Override
    public void deleteAll() {
        countryDao.deleteAll();
    }
}
