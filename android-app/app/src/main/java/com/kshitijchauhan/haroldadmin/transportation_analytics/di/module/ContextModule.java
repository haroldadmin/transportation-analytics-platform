package com.kshitijchauhan.haroldadmin.transportation_analytics.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context applicationContext) {
        this.context = applicationContext;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.context;
    }
}
