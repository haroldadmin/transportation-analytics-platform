package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home;

import com.kshitijchauhan.haroldadmin.transportation_analytics.TransportationAnalyticsApp;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.ApiManager;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    @Inject public ApiManager apiManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<RouteRequest>> mutableRouteRequestsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private SingleLiveEvent<String> mutableMessage = new SingleLiveEvent<>();

    public LiveData<List<RouteRequest>> routeRequestsList = mutableRouteRequestsList;
    public LiveData<String> message = mutableMessage;
    public LiveData<Boolean> isLoading = mutableIsLoading;

    public HomeViewModel() {
        super();
        TransportationAnalyticsApp
                .appComponent
                .inject(this);
    }

    public void getRouteRequests(int userId) {
        apiManager.getRouteRequests(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RouteRequest>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<RouteRequest> routeRequests) {
                        mutableRouteRequestsList.setValue(routeRequests);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableMessage.setValue("An error occurred");
                    }
                });
    }

}
