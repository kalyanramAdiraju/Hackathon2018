package com.appraisalmanagement.network;

import com.appraisalmanagement.models.AppraisalFormDataModel;
import com.appraisalmanagement.models.DataObjectModel;
import com.appraisalmanagement.models.GetAppraisalFormDataModel;
import com.appraisalmanagement.models.LoginModel;
import com.appraisalmanagement.models.MonthlyModel;
import com.appraisalmanagement.models.PostMontlyData;
import com.appraisalmanagement.models.ReporteesModel;
import com.appraisalmanagement.models.SampleJsonModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nineleaps on 31/5/18.
 */

public interface ApiInterface {

    @POST("/login")
    Call<LoginModel> sendLoginInfoToServer(@Body HashMap<String, Object> map);

    @GET
    Call<ResponseBody> downloadPdfs(String url);

    @POST("/saveFormData")
    Call<AppraisalFormDataModel> sendAppraisalFormData(@Query("userId") int userId, @Body HashMap<String, Object> map);

    @GET("/getFormData")
    Call<GetAppraisalFormDataModel> getAppraisalFormData(@Query("userId") int userId);

    @GET("/one/{empId}")
    Call<MonthlyModel> getMonthlyAssignmentsData(@Path("empId") String  empId);

    @POST("/one")
    Call<PostMontlyData> postMonthlyData(@Body HashMap<String, Object> map);

    @GET("/getRepoteeInfo")
    Call<ReporteesModel> getReporteesData(@Query("userId") int userId);
}
