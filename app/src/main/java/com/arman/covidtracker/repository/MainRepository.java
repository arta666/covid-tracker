package com.arman.covidtracker.repository;

import com.arman.covidtracker.contract.MainContract;
import com.arman.covidtracker.model.Global;
import com.arman.covidtracker.model.Summary;
import com.arman.covidtracker.service.ApiService;
import com.arman.covidtracker.service.NetworkService;

import io.reactivex.Single;

public class MainRepository implements MainContract.Repository {

    private ApiService service;

    public MainRepository(ApiService service) {
        this.service = service;
    }

    @Override
    public Single<Global> fetchGlobal() {
        return service.fetchGlobal();
    }
}
