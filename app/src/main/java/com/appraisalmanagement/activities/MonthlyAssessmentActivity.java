package com.appraisalmanagement.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.models.MonthlyModel;
import com.appraisalmanagement.models.PostMontlyData;
import com.appraisalmanagement.network.RestClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthlyAssessmentActivity extends AppCompatActivity {

    @BindView(R.id.description)
    EditText monthlyDescription;

    @BindView(R.id.submit)
    Button submit;

    @OnClick(R.id.submit)
    public void onSubmitForm(){
        if (!"".equals(monthlyDescription.getText().toString())){
            sendDataToServer();
        }
    }

    private void sendDataToServer() {
        HashMap<String,Object> maps=new HashMap<>();
        maps.put("monthlyData",monthlyDescription.getText().toString());
        Call<PostMontlyData> retrtofitCall= RestClient
                .getApplicationData()
                .postMonthlyData(maps);
        retrtofitCall.enqueue(new Callback<PostMontlyData>() {
            @Override
            public void onResponse(Call<PostMontlyData> call, Response<PostMontlyData> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MonthlyAssessmentActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(MonthlyAssessmentActivity.this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostMontlyData> call, Throwable t) {
                Toast.makeText(MonthlyAssessmentActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_assessment);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getDataFromServer();


    }

    private void getDataFromServer() {
        Call<MonthlyModel> retrtofitCall= RestClient
                .getApplicationData()
                .getMonthlyAssignmentsData();
        retrtofitCall.enqueue(new Callback<MonthlyModel>() {
            @Override
            public void onResponse(Call<MonthlyModel> call, Response<MonthlyModel> response) {
                if (response.isSuccessful()){
                    if (!"".equals(response.body().getData())){
                        monthlyDescription.setText(response.body().getData());
                    }
                }else {
                    Toast.makeText(MonthlyAssessmentActivity.this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MonthlyModel> call, Throwable t) {
                Toast.makeText(MonthlyAssessmentActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
