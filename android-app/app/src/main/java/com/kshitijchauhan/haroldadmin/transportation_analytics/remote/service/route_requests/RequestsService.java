package com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests;

import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request.CreateRouteRequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestsService {

    @GET("requests/")
    Single<List<RouteRequest>> getAllRouteRequests();

    @GET("requests/{requestId}")
    Single<RouteRequest> getRouteDetails(@Path("requestId") int requestId);

    @POST("requests/")
    Single<RouteRequest> createRouteRequest(@Body CreateRouteRequest request);

}
