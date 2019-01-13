package com.kshitijchauhan.haroldadmin.transportation_analytics.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class SharedPreferencesModule {

    private String name;

    public SharedPreferencesModule(String name) {
        this.name = name;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
