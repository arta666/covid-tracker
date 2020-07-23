package com.arman.covidtracker.repository;

import androidx.lifecycle.LiveData;

import com.arman.covidtracker.db.NewsDao;
import com.arman.covidtracker.model.News;

import java.util.List;

import javax.inject.Inject;

public class NewsDataSource implements NewsRepository {

    private NewsDao newsDao;

    @Inject
    public NewsDataSource(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public LiveData<List<News>> getNewsLiveData() {
        return newsDao.getNewsLiveData();
    }

    @Override
    public List<News> getNews() {
        return newsDao.getNews();
    }

    @Override
    public void insert(News news) {
        newsDao.insert(news);
    }

    @Override
    public void insertAll(List<News> news) {
        newsDao.insertAll(news);
    }

    @Override
    public void deleteAll() {
        newsDao.deleteAll();
    }
}
