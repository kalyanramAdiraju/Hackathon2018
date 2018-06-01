package com.appraisalmanagement.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by nineleaps on 31/5/18.
 */

public class RestClient {
    private static ApiInterface apiInterface = null;
    private static OkHttpClient.Builder client = new OkHttpClient.Builder();

    private RestClient() {
    }

    public static ApiInterface getApplicationData() {
        if (apiInterface == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setDateFormat(new SimpleDateFormat());
            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client.build())
                    .baseUrl("https://jsonplaceholder.typicode.com").build();
            apiInterface = retrofit.create(ApiInterface.class);

        }
        return apiInterface;
    }
}
