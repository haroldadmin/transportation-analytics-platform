package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.create_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.kshitijchauhan.haroldadmin.transportation_analytics.R;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request.CreateRouteRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.TransitionManager;

public class CreateRouteRequestFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = CreateRouteRequestFragment.class.getSimpleName();

    private CreateRouteRequestViewModel createRouteRequestViewModel;
    private MapView mapView;
    private TextInputEditText startEditText;
    private TextInputEditText endEditText;
    private MaterialButton btSendRequest;
    private MaterialButton btClearMap;
    private ProgressBar pbLoading;
    private Marker startMarker;
    private Marker endMarker;
    private GoogleMap map;

    private LatLng start;
    private LatLng end;

    public static CreateRouteRequestFragment newInstance() {
        return new CreateRouteRequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_route_request, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createRouteRequestViewModel = ViewModelProviders.of(this).get(CreateRouteRequestViewModel.class);

        createRouteRequestViewModel.createRequestSuccess.observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                Snackbar.make(getView(), "Request Sent Successfully!", Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        createRouteRequestViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                pbLoading.setVisibility(View.VISIBLE);
            } else {
                pbLoading.setVisibility(View.GONE);
            }
        });

        createRouteRequestViewModel.message.observe(getViewLifecycleOwner(), message -> {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mvCreateRequest);
        startEditText = view.findViewById(R.id.etStartPoint);
        endEditText = view.findViewById(R.id.etEndPoint);
        btSendRequest = view.findViewById(R.id.btSendRequest);
        pbLoading = view.findViewById(R.id.pbLoading);
        btClearMap = view.findViewById(R.id.btClearMap);

        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);

        btSendRequest.setOnClickListener(v -> {
            CreateRouteRequest request = new CreateRouteRequest();
            request.setStartPointLat(start.latitude);
            request.setStartPointLong(start.longitude);
            request.setEndPointLat(end.latitude);
            request.setEndPointLong(end.longitude);
            createRouteRequestViewModel.sendRequest(request);
        });

        btClearMap.setOnClickListener(v -> {
            if (this.map != null) {
                TransitionManager.beginDelayedTransition((ViewGroup) getView());
                map.clear();
                startMarker = null;
                endMarker = null;
                startEditText.setText(null);
                endEditText.setText(null);
                start = null;
                end = null;
                btClearMap.setVisibility(View.GONE);
                btSendRequest.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        googleMap.setOnMapClickListener(latLng -> {
            if (start == null) {
                start = latLng;
                createMarker(latLng, googleMap);
                updateOverlay(latLng, startEditText, googleMap);
            } else {
                if (endMarker != null) {
                    endMarker.remove();
                }
                end = latLng;
                createMarker(latLng, googleMap);
                updateOverlay(latLng, endEditText, googleMap);
            }
            TransitionManager.beginDelayedTransition((ViewGroup) getView());
            btClearMap.setVisibility(View.VISIBLE);
        });
    }

    private void createMarker(LatLng point, GoogleMap googleMap) {
        if (point.equals(start)) {
            startMarker = googleMap.addMarker(new MarkerOptions().position(point));
        } else {
            endMarker = googleMap.addMarker(new MarkerOptions().position(point));
        }
    }

    private void updateOverlay(LatLng point, TextInputEditText editText, GoogleMap map) {
        if (editText != null) {
            String text = String.format("%f, %f", point.latitude, point.longitude);
            editText.setText(text);
        }
        TransitionManager.beginDelayedTransition((ViewGroup) getView());
        if (this.start != null && this.end != null) {
            btSendRequest.setVisibility(View.VISIBLE);
        } else {
            btSendRequest.setVisibility(View.GONE);
        }
    }
}
