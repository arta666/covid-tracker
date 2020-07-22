package com.arman.covidtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.ActivityMainBinding;
import com.arman.covidtracker.di.component.ActivityComponent;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.di.module.FragmentModule;
import com.arman.covidtracker.presenter.BasePresenter;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.ui.base.BaseActivity;
import com.arman.covidtracker.ui.fragment.FragmentDrawer;
import com.arman.covidtracker.ui.fragment.MainFragment;
import com.arman.covidtracker.ui.fragment.SearchFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;

    @Inject
    MainRepository repository;

    @Inject
    FragmentManager mFragmentManager;

    private FragmentDrawer drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();

        setSupportActionBar(mBinding.toolbar.toolbar);

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);

        }
        drawerFragment = (FragmentDrawer)
                mFragmentManager.findFragmentById(R.id.fragment_navigation_drawer);


        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mBinding.toolbar.toolbar);
        drawerFragment.setDrawerListener(this);

        if (savedInstanceState == null) {

            displayView(0);
        }



    }


    @Override
    public View getContentView() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        return mBinding.getRoot();
    }

    @NonNull
    @Override
    protected BasePresenter createPresenter() {
        return null;
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

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new MainFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new SearchFragment();
                title = getString(R.string.title_search);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        }
    }
}