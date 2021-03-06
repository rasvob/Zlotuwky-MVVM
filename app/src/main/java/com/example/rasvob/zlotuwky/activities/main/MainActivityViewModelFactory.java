package com.example.rasvob.zlotuwky.activities.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.ExchangeConverter;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    private DataManager dataManager;
    private ExchangeConverter convertor;

    public MainActivityViewModelFactory(DataManager dataManager, ExchangeConverter convertor) {
        this.dataManager = dataManager;
        this.convertor = convertor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(dataManager, convertor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
