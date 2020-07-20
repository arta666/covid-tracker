package com.arman.covidtracker.contract;

import com.arman.covidtracker.model.Summary;
import com.arman.covidtracker.presenter.BasePresenter;
import com.arman.covidtracker.ui.base.BaseView;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MainContract {

    public interface Presenter {
        public void fetchSummary();
    }

    public interface Repository {
        Single<Summary> fetchSummary();
    }

    public interface View extends BaseView {
        public void onDisplaySummary(Summary summary);
        public void onDisplayEmptyScreen();
        public void onDisplayFailed(String message);
    }
}