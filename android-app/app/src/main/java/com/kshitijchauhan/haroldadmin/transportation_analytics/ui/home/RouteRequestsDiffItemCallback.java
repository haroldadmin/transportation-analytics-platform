package com.kshitijchauhan.haroldadmin.transportation_analytics.ui.home;

import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RouteRequestsDiffItemCallback extends DiffUtil.ItemCallback<RouteRequest> {

    @Override
    public boolean areItemsTheSame(@NonNull RouteRequest oldItem, @NonNull RouteRequest newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull RouteRequest oldItem, @NonNull RouteRequest newItem) {
        return oldItem.equals(newItem);
    }
}
