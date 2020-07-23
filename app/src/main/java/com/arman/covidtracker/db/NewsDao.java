package com.arman.covidtracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.arman.covidtracker.model.News;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    LiveData<List<News>> getNewsLiveData();


    @Query("SELECT * FROM news")
    List<News> getNews();


    @Insert(onConflict = REPLACE)
    void insert(News news);

    @Insert(onConflict = REPLACE)
    void insertAll(List<News> news);

    @Query("DELETE FROM news")
    void deleteAll();
}
