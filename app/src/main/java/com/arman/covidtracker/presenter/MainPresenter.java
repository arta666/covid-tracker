package com.arman.covidtracker.presenter;

import com.arman.covidtracker.contract.MainContract;
import com.arman.covidtracker.model.Global;
import com.arman.covidtracker.model.Summary;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private Scheduler mainScheduler;

    private MainContract.Repository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainPresenter(MainContract.View view, MainContract.Repository repository, Scheduler mainScheduler) {
        super(view);
        this.mainScheduler = mainScheduler;
        this.repository = repository;
    }


//    public void fetchSummaryData(){
//
//
//
//    }

    public void unsubscribe(){
        disposable.clear();
    }


    @Override
    public void fetchGlobal() {
        view.showProgress();

        disposable.add(repository.fetchGlobal()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<Global>() {
                    @Override
                    public void onSuccess(Global global) {
                        view.hideProgress();
                        if (global !=null){
                            view.onDisplayGlobal(global);
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
}
