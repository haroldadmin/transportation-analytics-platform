package com.kshitijchauhan.haroldadmin.transportation_analytics.remote.service.user.response;

import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expiry")
    private String expiryTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
