package com.arman.covidtracker.repository;

import com.arman.covidtracker.contract.SearchContract;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.CountryTotal;
import com.arman.covidtracker.service.ApiService;

import java.util.List;

import io.reactivex.Single;

public class SearchRepository implements SearchContract.Repository {

    private ApiService service;

    public SearchRepository(ApiService service) {
        this.service = service;
    }


    @Override
    public Single<List<Country>> fetchCountries() {
        return service.fetchCountries();
    }

    @Override
    public Single<List<CountryTotal>> fetchLiveByCountry(String countryName) {
        return service.fetchLiveByCountry(countryName);
    }
}
