package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kshitijchauhan.haroldadmin.transportation_analytics.R;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class RoutesAdapter extends ListAdapter<RouteRequest, RoutesViewHolder> {

    public RoutesAdapter(@NonNull DiffUtil.ItemCallback<RouteRequest> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_request, parent, false);
        return new RoutesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}