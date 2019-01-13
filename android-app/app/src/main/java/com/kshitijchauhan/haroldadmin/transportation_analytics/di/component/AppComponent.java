package com.kshitijchauhan.haroldadmin.transportation_analytics.di.component;

import com.kshitijchauhan.haroldadmin.transportation_analytics.MainActivity;
import com.kshitijchauhan.haroldadmin.transportation_analytics.di.module.ApiServiceModule;
import com.kshitijchauhan.haroldadmin.transportation_analytics.di.module.ContextModule;
import com.kshitijchauhan.haroldadmin.transportation_analytics.di.module.RetrofitModule;
import com.kshitijchauhan.haroldadmin.transportation_analytics.di.module.SharedPreferencesModule;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth.AuthViewModel;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.MainViewModel;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.create_request.CreateRouteRequestViewModel;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home.HomeFragment;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home.HomeViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ContextModule.class,
        RetrofitModule.class,
        ApiServiceModule.class,
        SharedPreferencesModule.class
})
@Singleton
public interface AppComponent {

    void inject(MainViewModel mainViewModel);

    void inject(AuthViewModel authViewModel);

    void inject(MainActivity mainActivity);

    void inject(HomeFragment homeFragment);

    void inject(HomeViewModel homeViewModel);

    void inject(CreateRouteRequestViewModel createRouteRequestViewModel);
}
