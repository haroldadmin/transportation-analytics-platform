package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth;

import android.os.Bundle;

import com.kshitijchauhan.haroldadmin.transportation_analytics.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Fade;

public class AuthenticationActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.auth_fragment_container, LoginFragment.newInstance())
                    .commit();
        }

        authViewModel.state.observe(this, newState -> {
            Fade fade = new Fade();
            Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id
                    .auth_fragment_container);

            switch (newState) {
                case LOGIN_STATE:

                    LoginFragment loginFragment = LoginFragment.newInstance();
                    loginFragment.setEnterTransition(fade);
                    existingFragment.setExitTransition(fade);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.auth_fragment_container, loginFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                case REGISTER_STATE:

                    RegisterFragment registerFragment = RegisterFragment.newInstance();
                    registerFragment.setEnterTransition(fade);
                    existingFragment.setExitTransition(fade);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.auth_fragment_container, registerFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    throw new IllegalStateException("Unable to update to state " +
                            newState.name() + " from AuthenticationActivity");
            }
        });
    }
}
