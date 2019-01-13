package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.create_request;

import com.kshitijchauhan.haroldadmin.transportation_analytics.TransportationAnalyticsApp;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.ApiManager;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request.CreateRouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.SingleLiveEvent;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateRouteRequestViewModel extends ViewModel {

    @Inject public ApiManager apiManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SingleLiveEvent<Boolean> mutableCreateRequestSuccess = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mutableIsLoading = new MutableLiveData<>();
    private SingleLiveEvent<String> mutableMessage = new SingleLiveEvent<>();

    public LiveData<Boolean> createRequestSuccess = mutableCreateRequestSuccess;
    public LiveData<Boolean> isLoading = mutableIsLoading;
    public LiveData<String> message = mutableMessage;

    public CreateRouteRequestViewModel() {
        super();
        TransportationAnalyticsApp.appComponent
                .inject(this);
    }

    public void sendRequest(CreateRouteRequest routeRequest) {
        apiManager.createRouteRequest(routeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RouteRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mutableIsLoading.setValue(true);
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(RouteRequest routeRequest) {
                        mutableIsLoading.setValue(false);
                        mutableCreateRequestSuccess.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableMessage.setValue("An error occurred");
                        mutableIsLoading.setValue(false);
                        mutableCreateRequestSuccess.setValue(false);
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
