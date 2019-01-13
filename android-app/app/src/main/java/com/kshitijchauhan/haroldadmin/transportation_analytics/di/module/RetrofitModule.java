package com.kshitijchauhan.haroldadmin.transportation_analytics.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.Constants;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.AuthInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ContextModule.class, SharedPreferencesModule.class})
public class RetrofitModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(AuthInterceptor authInterceptor, HttpLoggingInterceptor
            httpLoggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    AuthInterceptor provideAuthInterceptor(SharedPreferences sharedPreferences) {
        return new AuthInterceptor(sharedPreferences.getString(Constants.KEY_JWT_TOKEN, ""));
    }

    @Provides
    @Singleton
    Cache provideCache(Context context) {
        return new Cache(context.getCacheDir(), 5 * 1024 * 1024);
    }
}
