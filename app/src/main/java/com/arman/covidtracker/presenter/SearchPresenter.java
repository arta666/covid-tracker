package com.arman.covidtracker.presenter;

import android.util.Log;
import android.widget.SearchView;

import com.arman.covidtracker.contract.SearchContract;
import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.CountryTotal;
import com.arman.covidtracker.model.Global;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private Scheduler mainScheduler;

    private SearchContract.Repository repository;

    private CompositeDisposable disposable = new CompositeDisposable();


    public SearchPresenter(SearchContract.View view, SearchContract.Repository repository,Scheduler mainScheduler) {
        super(view);
        this.mainScheduler = mainScheduler;
        this.repository = repository;
    }





    @Override
    public void fetchCountries() {
        view.showProgress();

        disposable.add(repository.fetchCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> countries) {
                        view.hideProgress();
                        if (countries !=null){
                            view.onDisplayResult(countries);
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

    @Override
    public void fetchResult() {
        view.showProgress();

        ConnectableObservable<List<Country>> countryObservable = getCountries().replay();
        /**
         * Fetching all countries first
         * Observable emits List<Country> at once
         * All the items will be added to RecyclerView
         * */
        disposable.add(
                countryObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainScheduler)
                        .subscribeWith(new DisposableObserver<List<Country>>() {

                            @Override
                            public void onNext(List<Country> countries) {
                                view.hideProgress();
                                view.onDisplayResult(countries);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.onDisplayFailed(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        /**
         * Fetching individual countries status
         * First FlatMap converts single List<country> to multiple emissions
         * Second FlatMap makes HTTP call on each country emission
         * */
        disposable.add(
                countryObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainScheduler)
                        /**
                         * Converting List<country> emission to single Country emissions
                         * */
                        .flatMap(new Function<List<Country>, ObservableSource<Country>>() {
                            @Override
                            public ObservableSource<Country> apply(List<Country> countries) throws Exception {
                                return Observable.fromIterable(countries);
                            }
                        })
                        /**
                         * Fetching status on each Country emission
                         * */
                        .flatMap(new Function<Country, ObservableSource<Country>>() {
                            @Override
                            public ObservableSource<Country> apply(Country country) throws Exception {
                                return getCountryTotalObservable(country);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Country>() {

                            @Override
                            public void onNext(Country country) {
                              view.onDisplayCountryStatus(country);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onDisplayFailed(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        // Calling connect to start emission
        countryObservable.connect();
    }


    /**
     * Making Retrofit call to fetch all countries
     */
    private Observable<List<Country>> getCountries() {
        return repository.fetchCountries()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler);
    }

    /**
     * Making Retrofit call to get single country status
     * get status HTTP call returns Status object, but
     * map() operator is used to change the return type to country
     */
    private Observable<Country> getCountryTotalObservable(final Country country) {
        return repository
                .fetchLiveByCountry(country.getCountry().toLowerCase())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .map(new Function<List<CountryTotal>, Country>() {
                    @Override
                    public Country apply(List<CountryTotal> totals) throws Exception {
                        if (totals !=null && !totals.isEmpty()){
                            CountryTotal lastStatus = totals.get(totals.size() -1 );
//                            country.setNewConfirmed(lastStatus.getConfirmed());
//                            country.setNewDeaths(lastStatus.getDeaths());
//                            country.setNewRecovered(lastStatus.getRecovered());
                            return country;
                        }
                        return country;
                    }
                });
    }

    public void unsubscribe(){
        disposable.clear();
    }
}