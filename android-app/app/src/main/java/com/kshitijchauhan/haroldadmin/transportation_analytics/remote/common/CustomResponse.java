package com.kshitijchauhan.haroldadmin.transportation_analytics.remote.common;

import com.google.gson.annotations.SerializedName;

public class CustomResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
