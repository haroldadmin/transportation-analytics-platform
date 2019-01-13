package com.kshitijchauhan.haroldadmin.transportation_analytics.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RouteRequest {

    @SerializedName("id")
    private int id;

    @SerializedName("start_point_lat")
    private double startPointLat;

    @SerializedName("start_point_long")
    private double startPointLong;

    @SerializedName("end_point_lat")
    private double endPointLat;

    @SerializedName("end_point_long")
    private double endPointLong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getStartPointLong() {
        return startPointLong;
    }

    public void setStartPointLong(double startPointLong) {
        this.startPointLong = startPointLong;
    }

    public double getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getEndPointLong() {
        return endPointLong;
    }

    public void setEndPointLong(double endPointLong) {
        this.endPointLong = endPointLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteRequest that = (RouteRequest) o;
        return id == that.id &&
                Double.compare(that.startPointLat, startPointLat) == 0 &&
                Double.compare(that.startPointLong, startPointLong) == 0 &&
                Double.compare(that.endPointLat, endPointLat) == 0 &&
                Double.compare(that.endPointLong, endPointLong) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, startPointLat, startPointLong, endPointLat, endPointLong);
    }
}
