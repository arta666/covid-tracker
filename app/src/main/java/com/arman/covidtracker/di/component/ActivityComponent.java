package com.arman.covidtracker.di.component;

import com.arman.covidtracker.app.MyApplication;
import com.arman.covidtracker.di.module.ActivityModule;
import com.arman.covidtracker.di.module.FragmentModule;
import com.arman.covidtracker.di.scope.PerActivity;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.repository.NewsRemoteRepository;
import com.arman.covidtracker.repository.SearchRepository;
import com.arman.covidtracker.ui.activity.MainActivity;
import com.arman.covidtracker.ui.base.BaseActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    BaseActivity getActivity();

    void inject(MyApplication application);

    void inject(MainActivity mainActivity);

    MainRepository getMainRepo();

    SearchRepository getSearchRepo();

    NewsRemoteRepository getNewsRemoteRepository();

    FragmentComponent plus(FragmentModule fragmentModule);


}
