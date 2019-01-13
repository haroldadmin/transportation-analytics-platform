package com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user;

import com.kshitijchauhan.haroldadmin.transportation_analytics.models.RouteRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.models.User;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.common.CustomResponse;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserLoginRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.request.UserRegisterRequest;
import com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.response.UserLoginResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/all")
    Single<List<User>> getAllUsers();

    @GET("users/")
    Single<User> getUserProfile();

    @GET("users/{userId}")
    Single<User> getUserForId(@Path("userId") int userId);

    @GET("users/{userId}/requests")
    Single<List<RouteRequest>> getRoutesForUser(@Path("userId") int userId);

    @POST("users/register")
    Single<CustomResponse> registerUser(@Body UserRegisterRequest request);

    @POST("users/login")
    Single<UserLoginResponse> loginUser(@Body UserLoginRequest request);

}
