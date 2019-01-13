package com.kshitijchauhan.haroldadmin.transportation_analytics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request.CreateRouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.MainViewModel;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth.AuthenticationActivity;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth.LoginFragment;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.create_request.CreateRouteRequestFragment;
import com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home.HomeFragment;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.Constants;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.State;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Fade;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    public SharedPreferences sharedPreferences;

    private Toolbar toolbar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TransportationAnalyticsApp.appComponent
                .inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        boolean isAuthenticated = sharedPreferences.getBoolean(Constants.KEY_IS_AUTHENTICATED,
                false);

        if (!isAuthenticated) {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, HomeFragment.newInstance())
                    .commit();
        }

        mainViewModel.state.observe(this, newState -> {
            handleStateChange(newState);
        });
    }

    private void handleStateChange(State newState) {

        Fade fade = new Fade();
        Fragment existingFragment = getSupportFragmentManager()
                .findFragmentById(R.id.main_fragment_container);

        switch (newState) {
            case HOME_STATE:

                HomeFragment homeFragment = HomeFragment.newInstance();
                homeFragment.setEnterTransition(fade);
                existingFragment.setExitTransition(fade);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, homeFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case CREATE_REQUEST_STATE:

                CreateRouteRequestFragment routeRequestFragment = CreateRouteRequestFragment
                        .newInstance();
                routeRequestFragment.setEnterTransition(fade);
                existingFragment.setExitTransition(fade);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, routeRequestFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case LOGIN_STATE:

                LoginFragment loginFragment = LoginFragment.newInstance();
                loginFragment.setEnterTransition(fade);
                existingFragment.setExitTransition(fade);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, loginFragment)
                        .addToBackStack(null)
                        .commit();
                break;

            default:
                throw new IllegalStateException("Unknown state: " + newState.name());
        }
    }
}
