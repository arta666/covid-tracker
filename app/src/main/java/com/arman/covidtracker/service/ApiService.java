package com.arman.covidtracker.service;

import com.arman.covidtracker.app.Endpoints;
import com.arman.covidtracker.model.Summary;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET(Endpoints.GET_SUMMARY)
    Single<Summary> fetchSummary();
}
