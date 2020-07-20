package com.arman.covidtracker.service;

import com.arman.covidtracker.model.Summary;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Single<Summary> fetchSummary();
}
