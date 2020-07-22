package com.arman.covidtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.ActivityMainBinding;
import com.arman.covidtracker.di.component.ActivityComponent;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.di.module.FragmentModule;
import com.arman.covidtracker.presenter.BasePresenter;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.ui.base.BaseActivity;
import com.arman.covidtracker.ui.fragment.MainFragment;
import com.arman.covidtracker.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;

    @Inject
    MainRepository repository;

    @Inject
    FragmentManager mFragmentManager;

    private List<Fragment> fragmentList;

    private List<String> fragmentTitle;

    Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentList = new ArrayList<>();
        fragmentTitle = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new SearchFragment());

        fragmentTitle.add("Home");
        fragmentTitle.add("Search");
        createFragments(0);

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



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(mFragment, 0);
                    mFragment = fragmentList.get(0);
                    break;

                case R.id.navigation_search:
                    switchFragment(mFragment, 1);
                    mFragment = fragmentList.get(1);
                    break;
            }
            return true;

        }


    };


    private void switchFragment(Fragment currentTab, int position) {
        Fragment fragment = fragmentList.get(position);
        String tag = fragmentTitle.get(position);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(tag) == null) {
            transaction.add(R.id.main_container, fragment, tag);
        }
        transaction.hide(currentTab);
        transaction.show(fragment);

        transaction.commit();
//        if (fragment instanceof MainFragment) {
//            getBaseActionBar().setDisplayShowCustomEnabled(true);
//            return;
//        } else if (fragment instanceof SearchFragment) {
//            getBaseActionBar().setDisplayShowCustomEnabled(false);
//            setToolbarTitle(tag);
//            return;
//        }
//        getBaseActionBar().setDisplayShowCustomEnabled(false);
//        setToolbarTitle(tag);


    }

    //Add all the fragments that need to be added and hidden. Also, add the one that is supposed to be the starting one, that one is not hidden.
    private void createFragments(int position) {

        mFragment = fragmentList.get(position);
        mFragmentManager
                .beginTransaction()
                .add(R.id.main_container, mFragment, fragmentTitle.get(position))
                .commit();


    }







}