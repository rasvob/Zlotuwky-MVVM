package com.example.rasvob.zlotuwky.activities.main;

import com.example.rasvob.zlotuwky.di.scopes.PerActivity;
import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.ExchangeConverter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @PerActivity
    MainActivityViewModelFactory factory(DataManager dataManager, ExchangeConverter convertor) {
        return new MainActivityViewModelFactory(dataManager, convertor);
    }

    @Provides
    @PerActivity
    ExchangeConverter convertor() {
        return new ExchangeConverter();
    }
}
