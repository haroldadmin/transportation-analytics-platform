package com.kshitijchauhan.haroldadmin.transportation_analytics.remote;

import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.User;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.common.CustomResponse;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.RequestsService;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.route_requests.request.CreateRouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.UserService;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserLoginRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserRegisterRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.response.UserLoginResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ApiManager {

    private UserService userService;
    private RequestsService requestsService;

    @Inject
    public ApiManager(UserService userService, RequestsService requestsService) {
        this.userService = userService;
        this.requestsService = requestsService;
    }

    public Single<UserLoginResponse> login(UserLoginRequest userLoginRequest) {
        return userService.loginUser(userLoginRequest);
    }

    public Single<User> getUserProfile() {
        return userService.getUserProfile();
    }

    public Single<List<RouteRequest>> getRouteRequests(int userId) {
        return userService.getRoutesForUser(userId);
    }

    public Single<RouteRequest> createRouteRequest(CreateRouteRequest request) {
        return requestsService.createRouteRequest(request);
    }

    public Single<CustomResponse> register(UserRegisterRequest request) {
        return userService.registerUser(request);
    }

}
