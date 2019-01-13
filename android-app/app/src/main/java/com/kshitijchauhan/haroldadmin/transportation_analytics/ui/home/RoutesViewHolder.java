package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kshitijchauhan.haroldadmin.transportation_analytics.R;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutesViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView tvRouteName;

    private LatLng start;
    private LatLng end;
    private LatLngBounds bounds;
    private CameraUpdate cameraUpdate;

    public RoutesViewHolder(@NonNull View itemView) {
        super(itemView);

        mapView = itemView.findViewById(R.id.mapView);
        tvRouteName = itemView.findViewById(R.id.tvRouteName);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    public void bind(RouteRequest routeRequest) {
        start = new LatLng(routeRequest.getStartPointLat(), routeRequest.getStartPointLong());
        end = new LatLng(routeRequest.getEndPointLat(), routeRequest.getEndPointLong());

        int margin = itemView.getContext().getResources().getDimensionPixelOffset(R.dimen
                .marker_map_border_margin);

        bounds = new LatLngBounds.Builder()
                .include(start)
                .include(end)
                .build();
        cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        tvRouteName.setText(String.format("Route #%d", getAdapterPosition() + 1));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(start));
        googleMap.addMarker(new MarkerOptions().position(end));
        googleMap.moveCamera(cameraUpdate);
    }
}
