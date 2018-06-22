package com.example.rasvob.zlotuwky.di.components;

import com.example.rasvob.zlotuwky.ZlotuwkyApplication;
import com.example.rasvob.zlotuwky.di.modules.ZlotuwkyAppModule;
import com.example.rasvob.zlotuwky.di.modules.ZlotuwkyBuildersModule;
import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.PageDownloadService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ZlotuwkyAppModule.class, ZlotuwkyBuildersModule.class})
public interface ZlotuwkyAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ZlotuwkyApplication app);
        ZlotuwkyAppComponent build();
    }

    void inject(ZlotuwkyApplication app);
//    DataManager dataManager();
}
