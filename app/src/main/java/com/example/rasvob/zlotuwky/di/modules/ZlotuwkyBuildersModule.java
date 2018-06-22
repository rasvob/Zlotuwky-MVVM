package com.example.rasvob.zlotuwky.di.modules;

import com.example.rasvob.zlotuwky.activities.main.MainActivity;
import com.example.rasvob.zlotuwky.activities.main.MainActivityModule;
import com.example.rasvob.zlotuwky.di.scopes.PerActivity;
import com.example.rasvob.zlotuwky.di.scopes.PerFragment;
import com.example.rasvob.zlotuwky.fragments.SetOwnRateFragmentDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ZlotuwkyBuildersModule {
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    @PerActivity
    abstract MainActivity bindMainActivity();
}
