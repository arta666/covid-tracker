package com.arman.covidtracker.service;

import com.arman.covidtracker.app.Endpoints;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.CountryTotal;
import com.arman.covidtracker.model.Global;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.model.NewsResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Endpoints.GET_GLOBAL_STATUS)
    Single<Global> fetchGlobal();

    @GET(Endpoints.GET_COUNTRIES)
    Single<List<Country>> fetchCountries();

    @GET(Endpoints.GET_LIVE_BY_COUNTRY)
    Single<List<CountryTotal>> fetchLiveByCountry(@Path("countryName") String countryName);


    @GET(Endpoints.GET_TRENDING_NEWS)
    Single<NewsResponse> fetchTrendingNes(@Query("limit") int limit);
}
