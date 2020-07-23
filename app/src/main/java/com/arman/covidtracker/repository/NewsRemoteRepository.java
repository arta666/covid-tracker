package com.arman.covidtracker.repository;

import com.arman.covidtracker.contract.NewsContract;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.model.NewsResponse;
import com.arman.covidtracker.service.ApiService;

import java.util.List;

import io.reactivex.Single;

public class NewsRemoteRepository implements NewsContract.Repository {

    private ApiService service;

    public NewsRemoteRepository(ApiService service) {
        this.service = service;
    }


    @Override
    public Single<NewsResponse> fetchTrendingNews(int limit) {
        return service.fetchTrendingNes(limit);
    }
}
