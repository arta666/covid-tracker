package com.arman.covidtracker.di.component;

import androidx.fragment.app.Fragment;

import com.arman.covidtracker.di.module.FragmentModule;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(@NotNull Fragment fragment);

}