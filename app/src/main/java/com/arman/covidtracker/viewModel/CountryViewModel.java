package com.arman.covidtracker.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.model.Country;

import java.util.List;

import javax.inject.Inject;


public class CountryViewModel extends AndroidViewModel {

    @Inject
    AppDb db;

    private LiveData<List<Country>> countries;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        if (db != null) {
            countries = db.countryDao().getCountriesLiveData();
        }
    }


    public LiveData<List<Country>> getCountries() {
        return countries;
    }

}