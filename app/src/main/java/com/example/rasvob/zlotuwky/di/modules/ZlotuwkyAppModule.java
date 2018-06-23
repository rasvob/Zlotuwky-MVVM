package com.example.rasvob.zlotuwky.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rasvob.zlotuwky.ZlotuwkyApplication;
import com.example.rasvob.zlotuwky.di.scopes.ApplicationContext;
import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.PageDownloadService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class ZlotuwkyAppModule {
    @Provides
    @ApplicationContext
    @Singleton
    public Context context(ZlotuwkyApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
    }
    @Provides
    @Singleton
    public Cache cache(File file) {
        return new Cache(file, 10 * 1000 * 1000);
    }

    @Provides
    @Singleton
    public File file(@ApplicationContext Context context) {
        return context.getCacheDir();
    }

    @Provides
    @Singleton
    public PageDownloadService pageDownloadService(OkHttpClient client) {
        return new PageDownloadService(client);
    }

    @Provides
    @Singleton
    public SharedPreferences sharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences("SharedPrefsZlotuwky", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public DataManager dataManager(SharedPreferences sharedPreferences, PageDownloadService pageDownloadService) {
        return new DataManager(sharedPreferences, pageDownloadService);
    }
}
