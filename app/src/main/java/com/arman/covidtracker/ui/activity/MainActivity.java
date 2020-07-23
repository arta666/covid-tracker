package com.arman.covidtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
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

import com.arman.xnavigation.utils.NavigationExtensionsKt;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;

    @Inject
    MainRepository repository;

    @Inject
    FragmentManager mFragmentManager;

    private LiveData<NavController> currentNavController = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mBinding.toolbar);

        Log.d(TAG, "onCreate: " + (savedInstanceState == null) );

        if (savedInstanceState == null) {
            setUpNavigation();

        }
    }

    private void setUpNavigation() {
        List<Integer> navGraphIds = Arrays.asList(R.navigation.home,
                R.navigation.search,R.navigation.news);

        Intent intent = this.getIntent();

        LiveData<NavController> controller = NavigationExtensionsKt.setupWithNavController(mBinding.navigation,
                navGraphIds, mFragmentManager, R.id.main_container, intent);


        controller.observe(this, new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                Log.d(TAG, "onChanged: " + navController.getCurrentDestination());
//                NavigationUI.setupWithNavController(mBinding.toolbar, navController);


                NavigationUI.setupActionBarWithNavController(MainActivity.this,navController);
            }
        });

        currentNavController = controller;
    }


    @Override
    public boolean onSupportNavigateUp() {
        if (currentNavController != null) {
            NavController controller = (NavController) currentNavController.getValue();
            if (controller != null) {
                return controller.navigateUp();
            }
        }
        return false;
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
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setUpNavigation();
    }

}