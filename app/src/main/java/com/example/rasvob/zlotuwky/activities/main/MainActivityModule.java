package com.example.rasvob.zlotuwky.activities.main;

import com.example.rasvob.zlotuwky.di.modules.ZlotuwkyAppModule;
import com.example.rasvob.zlotuwky.di.scopes.PerActivity;
import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.ExchangeConvertor;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @PerActivity
    MainActivityViewModelFactory factory(DataManager dataManager, ExchangeConvertor convertor) {
        return new MainActivityViewModelFactory(dataManager, convertor);
    }

    @Provides
    @PerActivity
    ExchangeConvertor convertor() {
        return new ExchangeConvertor();
    }
}
