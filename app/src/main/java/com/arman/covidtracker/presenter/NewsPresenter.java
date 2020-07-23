package com.arman.covidtracker.presenter;

import android.util.Log;
import android.widget.SearchView;

import androidx.core.widget.NestedScrollView;

import com.arman.covidtracker.contract.NewsContract;
import com.arman.covidtracker.contract.SearchContract;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.model.NewsResponse;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter extends BasePresenter<NewsContract.View>{

    private static final String TAG = NewsPresenter.class.getSimpleName();

    private Scheduler mainScheduler;

    private NewsContract.Repository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public NewsPresenter(NewsContract.View view,NewsContract.Repository repository, Scheduler mainScheduler) {
        super(view);
        this.mainScheduler = mainScheduler;
        this.repository = repository;
    }


    public void fetchTrendingNews(int limit){
        disposable.add(repository.fetchTrendingNews(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<NewsResponse>() {
                    @Override
                    public void onSuccess(NewsResponse response) {
                        view.hideProgress();
                        if (response.getItems() !=null && !response.getItems().isEmpty()){
                            view.onDisplayResult(response.getItems());
                        }else {
                            view.onDisplayEmptyScreen();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        view.onDisplayFailed(e.getLocalizedMessage());
                    }
                }));
    }






    public void unsubscribe(){
        disposable.clear();
    }



}
