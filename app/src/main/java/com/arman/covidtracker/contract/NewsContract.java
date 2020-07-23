package com.arman.covidtracker.contract;

import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.CountryTotal;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.model.NewsResponse;
import com.arman.covidtracker.ui.base.BaseView;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Path;

public interface NewsContract {


    public interface Presenter {
        public void fetchNews();
    }

    public interface Repository {
        Single<NewsResponse> fetchTrendingNews(int limit);
    }

    public interface View extends BaseView {
        public void onDisplayResult(List<News> news);
        public void onDisplayEmptyScreen();
        public void onDisplayFailed(String message);

    }
}
