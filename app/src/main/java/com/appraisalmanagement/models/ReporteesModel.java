package com.appraisalmanagement.models;

import java.util.List;

/**
 * Created by nineleaps on 2/6/18.
 */

public class ReporteesModel {
    String status;
    List<ReportessDataModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReportessDataModel> getData() {
        return data;
    }

    public void setData(List<ReportessDataModel> data) {
        this.data = data;
    }
}
