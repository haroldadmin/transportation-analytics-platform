package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kshitijchauhan.haroldadmin.transportation_analytics.MainActivity;
import com.kshitijchauhan.haroldadmin.transportation_analytics.R;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.State;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btLogin;
    private ProgressBar pbLoading;
    private TextView tvNoAccount;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);

        authViewModel.message.observe(getViewLifecycleOwner(), message -> {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        });

        authViewModel.userLoginSuccessState.observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        authViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                pbLoading.setVisibility(View.VISIBLE);
            } else {
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btLogin = view.findViewById(R.id.btLogin);
        pbLoading = view.findViewById(R.id.pbLoading);
        tvNoAccount = view.findViewById(R.id.tvNoAccount);

        btLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            authViewModel.login(email, password);
        });

        tvNoAccount.setOnClickListener(v -> {
            authViewModel.updateState(State.REGISTER_STATE);
        });
    }
}
