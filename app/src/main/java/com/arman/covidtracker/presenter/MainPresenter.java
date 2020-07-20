package com.arman.covidtracker.presenter;

import com.arman.covidtracker.contract.MainContract;
import com.arman.covidtracker.model.Summary;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> {

    private Scheduler mainScheduler;

    private MainContract.Repository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainPresenter(MainContract.View view, MainContract.Repository repository, Scheduler mainScheduler) {
        super(view);
        this.mainScheduler = mainScheduler;
        this.repository = repository;
    }


    public void fetchSummaryData(){

        view.showProgress();

        disposable.add(repository.fetchSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<Summary>() {
                    @Override
                    public void onSuccess(Summary summary) {
                        view.hideProgress();
                        if (summary !=null){
                            view.onDisplaySummary(summary);
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
