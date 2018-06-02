package com.appraisalmanagement.models;

/**
 * Created by nineleaps on 2/6/18.
 */

public class GetAppraisalFormDataModel {
    String status;
    GetAppraisalFormDataModelData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetAppraisalFormDataModelData getData() {
        return data;
    }

    public void setData(GetAppraisalFormDataModelData data) {
        this.data = data;
    }
}
