package com.appraisalmanagement.network;

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

/**
 * Created by nineleaps on 31/5/18.
 */

public interface ApiInterface {

    @POST("/login")
    Call<LoginModel> sendLoginInfoToServer(@Header("Authorization")String token, @Body HashMap<String,Object> map);

    @GET
    Call<ResponseBody> downloadPdfs(String url);

    @GET("/posts")
    Call<List<DataObjectModel>> getSampleData();
}
