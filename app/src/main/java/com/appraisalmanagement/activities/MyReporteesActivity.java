package com.appraisalmanagement.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.adapters.MyReporteesAdapter;
import com.appraisalmanagement.adapters.SendReporteeDataInterface;
import com.appraisalmanagement.models.ReporteesModel;
import com.appraisalmanagement.models.ReportessDataModel;
import com.appraisalmanagement.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReporteesActivity extends AppCompatActivity {

    int userId;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    MyReporteesAdapter myReporteesAdapter;
    List<ReportessDataModel> dataList;
    int reporteeUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reportees);
        ButterKnife.bind(this);
        dataList=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromLocalStorage();
        getReporteesData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataFromLocalStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails",
                MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
    }

    private void getReporteesData() {
        final Call<ReporteesModel> reporteesCall= RestClient
                .getApplicationData()
                .getReporteesData(userId);
        reporteesCall.enqueue(new Callback<ReporteesModel>() {
            @Override
            public void onResponse(Call<ReporteesModel> call,
                                   Response<ReporteesModel> response) {
                if (response.isSuccessful()){
                    dataList.addAll(response.body().getData());
                    setAdapter(response.body().getData());
                }else {
                    Toast.makeText(MyReporteesActivity.this,
                            "Something went wrong!"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteesModel> call, Throwable t) {
                Log.d("hello","3");
                Toast.makeText(MyReporteesActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<ReportessDataModel> data) {
        myReporteesAdapter=new MyReporteesAdapter(this, dataList, new SendReporteeDataInterface() {
            @Override
            public void sendUserIdToActivity(int userId) {
                reporteeUserId = userId;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(myReporteesAdapter);
    }


}
