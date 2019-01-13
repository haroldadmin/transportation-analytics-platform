package com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request;

import com.google.gson.annotations.SerializedName;

public class CreateRouteRequest {

    @SerializedName("start_point_lat")
    private double startPointLat;

    @SerializedName("end_point_lat")
    private double endPointLat;

    @SerializedName("start_point_long")
    private double startPointLong;

    @SerializedName("end_point_long")
    private double endPointLong;

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getStartPointLong() {
        return startPointLong;
    }

    public void setStartPointLong(double startPointLong) {
        this.startPointLong = startPointLong;
    }

    public double getEndPointLong() {
        return endPointLong;
    }

    public void setEndPointLong(double endPointLong) {
        this.endPointLong = endPointLong;
    }
}
