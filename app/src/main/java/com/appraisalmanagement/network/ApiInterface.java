package com.appraisalmanagement.network;

import com.appraisalmanagement.models.AppraisalFormDataModel;
import com.appraisalmanagement.models.DataObjectModel;
import com.appraisalmanagement.models.LoginModel;
import com.appraisalmanagement.models.SampleJsonModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nineleaps on 31/5/18.
 */

public interface ApiInterface {

    @GET("/login")
    Call<LoginModel> sendLoginInfoToServer(@Query("email") String email,@Query("password") String password);

    @GET
    Call<ResponseBody> downloadPdfs(String url);

    @POST("/saveFormData")
    Call<AppraisalFormDataModel> sendAppraisalFormData(@Query("userId") int userId,@Body HashMap<String,Object> map);
}
