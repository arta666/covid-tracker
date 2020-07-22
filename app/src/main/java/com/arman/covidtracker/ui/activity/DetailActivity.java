package com.arman.covidtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.ActivityDetailBinding;
import com.arman.covidtracker.di.component.ActivityComponent;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.presenter.BasePresenter;
import com.arman.covidtracker.ui.base.BaseActivity;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity {

    ActivityDetailBinding mBinding;

    @Inject
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View getContentView() {
        mBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @NonNull
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentComponent initComponent(ActivityComponent activityComponent) {
        return null;
    }
}