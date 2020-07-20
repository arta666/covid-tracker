package com.arman.covidtracker.di.module;

import androidx.fragment.app.FragmentManager;

import com.arman.covidtracker.di.scope.PerActivity;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.service.ApiService;
import com.arman.covidtracker.ui.base.BaseActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    BaseActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    FragmentManager fragmentManager(BaseActivity baseActivity) {
        return baseActivity.getSupportFragmentManager();
    }

    @PerActivity
    @Provides
    MainRepository provideMainRepo(ApiService service){
        return new MainRepository(service);
    }

    @Provides
    @Named("activity")
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

}