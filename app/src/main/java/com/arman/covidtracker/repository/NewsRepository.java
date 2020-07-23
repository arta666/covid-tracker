package com.arman.covidtracker.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.arman.covidtracker.model.News;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface NewsRepository {

    LiveData<List<News>> getNewsLiveData();

    List<News> getNews();

    void insert(News news);

    void insertAll(List<News> news);

    void deleteAll();

}
