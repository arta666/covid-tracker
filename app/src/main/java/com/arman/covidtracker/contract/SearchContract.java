package com.arman.covidtracker.contract;

import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.CountryTotal;
import com.arman.covidtracker.model.Summary;
import com.arman.covidtracker.ui.base.BaseView;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Path;

public interface SearchContract {

    public interface Presenter {
        public void fetchCountries();
        public void fetchResult();
    }

    public interface Repository {
        Single<List<Country>> fetchCountries();

        Single<List<CountryTotal>> fetchLiveByCountry(@Path("countryName") String countryName);
    }

    public interface View extends BaseView {
        public void onDisplayResult(List<Country> country);
        public void onDisplayEmptyScreen();
        public void onDisplayFailed(String message);
        public void onDisplayCountryStatus(Country country);
    }
}
