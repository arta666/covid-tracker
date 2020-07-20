package com.arman.covidtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arman.covidtracker.R;
import com.arman.covidtracker.contract.MainContract;
import com.arman.covidtracker.databinding.ActivityMainBinding;
import com.arman.covidtracker.di.component.ActivityComponent;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.di.module.FragmentModule;
import com.arman.covidtracker.model.Summary;
import com.arman.covidtracker.presenter.MainPresenter;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.service.ApiService;
import com.arman.covidtracker.service.NetworkService;
import com.arman.covidtracker.ui.base.BaseActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;

    @Inject
    MainRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.fetchSummaryData();

    }

    @Override
    public View getContentView() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @NonNull
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, repository, AndroidSchedulers.mainThread());
    }

    @Override
    public FragmentComponent initComponent(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return activityComponent.plus(new FragmentModule());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void onDisplaySummary(Summary summary) {
        Log.d(TAG, "onDisplaySummary: " + summary);
    }

    @Override
    public void onDisplayEmptyScreen() {
        Log.d(TAG, "onDisplayEmptyScreen: ");
    }

    @Override
    public void onDisplayFailed(String message) {
        Log.d(TAG, "onDisplayFailed: " + message);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");

    }
}