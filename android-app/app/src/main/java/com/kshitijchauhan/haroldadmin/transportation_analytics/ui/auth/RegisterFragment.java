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

public class RegisterFragment extends Fragment {

    private AuthViewModel authViewModel;
    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btRegister;
    private TextView tvExistingAccount;
    private ProgressBar pbLoading;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btRegister = view.findViewById(R.id.btRegister);
        tvExistingAccount = view.findViewById(R.id.tvExistingAccount);
        pbLoading = view.findViewById(R.id.pbLoading);

        tvExistingAccount.setOnClickListener(v -> {
            authViewModel.updateState(State.LOGIN_STATE);
        });

        btRegister.setOnClickListener(v -> {
            authViewModel.register(
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    etPassword.getText().toString()
            );
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);

        authViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                pbLoading.setVisibility(View.VISIBLE);
            } else {
                pbLoading.setVisibility(View.GONE);
            }
        });

        authViewModel.message.observe(getViewLifecycleOwner(), message -> {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        });

        authViewModel.userRegisterSuccessState.observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }
}
