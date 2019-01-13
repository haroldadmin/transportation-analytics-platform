package com.kshitijchauhan.haroldadmin.transportation_analytics.ui;

import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.SingleLiveEvent;
import com.kshitijchauhan.haroldadmin.transportation_analytics.utilities.State;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private SingleLiveEvent<State> mutableState = new SingleLiveEvent<>();

    public LiveData<State> state = mutableState;

    public MainViewModel() {
        super();
    }

    public void updateState(State newState) {
        mutableState.setValue(newState);
    }

}
