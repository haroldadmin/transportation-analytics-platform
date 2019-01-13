package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.auth;

import android.content.SharedPreferences;
import android.util.Log;

import com.kshitijchauhan.haroldadmin.transportation_analytics.models.User;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserRegisterRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.Constants;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.SingleLiveEvent;
import com.kshitijchauhan.haroldadmin.transportation_analytics.TransportationAnalyticsApp;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.ApiManager;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.AuthInterceptor;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserLoginRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.response.UserLoginResponse;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.State;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = AuthViewModel.class.getSimpleName();

    @Inject
    public ApiManager apiManager;
    @Inject
    public SharedPreferences sharedPreferences;
    @Inject
    public AuthInterceptor authInterceptor;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SingleLiveEvent<Boolean> mutableLoginSuccessState = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mutableRegisterSuccessState = new SingleLiveEvent<>();
    private SingleLiveEvent<String> mutableMessage = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private SingleLiveEvent<State> mutableState = new SingleLiveEvent<>();

    public LiveData<Boolean> userLoginSuccessState = mutableLoginSuccessState;
    public LiveData<String> message = mutableMessage;
    public LiveData<Boolean> isLoading = mutableIsLoading;
    public LiveData<State> state = mutableState;
    public LiveData<Boolean> userRegisterSuccessState = mutableRegisterSuccessState;

    public AuthViewModel() {
        super();
        TransportationAnalyticsApp.appComponent.inject(this);
    }

    public void login(String email, String password) {
        UserLoginRequest request = new UserLoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        apiManager.login(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mutableIsLoading.postValue(true);
                })
                .doOnSuccess(userLoginResponse -> {
                    sharedPreferences
                            .edit()
                            .putString(Constants.KEY_JWT_TOKEN, userLoginResponse.getAccessToken())
                            .apply();

                    authInterceptor.setToken(userLoginResponse.getAccessToken());
                })
                .flatMap((Function<UserLoginResponse, SingleSource<User>>) userLoginResponse ->
                        apiManager.getUserProfile())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(User user) {
                        sharedPreferences.edit()
                                .putInt(Constants.KEY_USER_ID, user.getId())
                                .putBoolean(Constants.KEY_IS_AUTHENTICATED, true)
                                .apply();
                        mutableIsLoading.postValue(false);
                        mutableLoginSuccessState.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableLoginSuccessState.setValue(false);
                        mutableIsLoading.setValue(false);
                        mutableMessage.setValue("An error occurred");
                    }
                });
    }

    public void register(String name, String email, String password) {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);

        apiManager.register(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mutableIsLoading.postValue(true);
                })
                .flatMap(customResponse -> {
                    Log.d(TAG, "Received response: " + customResponse.getMessage());
                    UserLoginRequest loginRequest = new UserLoginRequest();
                    loginRequest.setEmail(email);
                    loginRequest.setPassword(password);
                    return apiManager.login(loginRequest);
                })
                .doOnSuccess(userLoginResponse -> {
                    Log.d(TAG, "Got user login response");
                    sharedPreferences
                            .edit()
                            .putString(Constants.KEY_JWT_TOKEN, userLoginResponse.getAccessToken())
                            .apply();

                    authInterceptor.setToken(userLoginResponse.getAccessToken());
                })
                .flatMap( userLoginResponse -> {
                    Log.d(TAG, "Fetching user profile");
                    return apiManager.getUserProfile();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(User user) {
                        Log.d(TAG, "Login and registration successful");
                        sharedPreferences.edit()
                                .putInt(Constants.KEY_USER_ID, user.getId())
                                .putBoolean(Constants.KEY_IS_AUTHENTICATED, true)
                                .apply();
                        mutableIsLoading.postValue(false);
                        mutableRegisterSuccessState.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableRegisterSuccessState.setValue(false);
                        mutableIsLoading.setValue(false);
                        mutableMessage.setValue("An error occurred");
                    }
                });
    }

    public void updateState(State newState) {
        mutableState.setValue(newState);
    }

}
