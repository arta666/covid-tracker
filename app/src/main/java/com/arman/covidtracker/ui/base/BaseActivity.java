package com.arman.covidtracker.ui.base;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arman.covidtracker.app.MyApplication;
import com.arman.covidtracker.di.HasComponent;
import com.arman.covidtracker.di.component.ActivityComponent;
import com.arman.covidtracker.di.component.DaggerActivityComponent;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.di.module.ActivityModule;
import com.arman.covidtracker.presenter.BasePresenter;

public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity implements HasComponent {

    protected Presenter presenter;

    private ActivityComponent build;

    FragmentComponent component;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        build = DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component = initComponent(build);

        presenter = createPresenter();

    }

    public abstract View getContentView();

    @NonNull
    protected abstract Presenter createPresenter();


    @Override
    public FragmentComponent getComponent() {
        if (component == null)
            if (build != null) {
                component = initComponent(build);
            } else {
                build = DaggerActivityComponent.builder()
                        .applicationComponent(MyApplication.getInstance().getComponent())
                        .activityModule(new ActivityModule(this))
                        .build();
                component = initComponent(build);
            }

        return component;
    }

    public abstract FragmentComponent initComponent(ActivityComponent activityComponent);
}

