package com.appraisalmanagement.models;

/**
 * Created by nineleaps on 31/5/18.
 */

public class LoginModel {
    String status;
    LoginDataModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginDataModel getData() {
        return data;
    }

    public void setData(LoginDataModel data) {
        this.data = data;
    }
}
