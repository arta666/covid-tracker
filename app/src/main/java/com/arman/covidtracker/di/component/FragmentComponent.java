package com.arman.covidtracker.di.component;

import androidx.fragment.app.Fragment;

import com.arman.covidtracker.di.module.FragmentModule;
import com.arman.covidtracker.ui.fragment.MainFragment;
import com.arman.covidtracker.ui.fragment.SearchFragment;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(@NotNull MainFragment fragment);

    void inject(@NotNull SearchFragment fragment);

}