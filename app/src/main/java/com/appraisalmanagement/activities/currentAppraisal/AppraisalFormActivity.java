package com.appraisalmanagement.activities.currentAppraisal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.adapters.AppraisalAdapter;
import com.appraisalmanagement.adapters.ImpactInterface;
import com.appraisalmanagement.models.AppraisalFormDataModel;
import com.appraisalmanagement.network.RestClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppraisalFormActivity extends AppCompatActivity {
    HashMap<String,Object>mapTest=new HashMap<>();
    @BindView(R.id.recycler_view_form)
    RecyclerView recyclerView;
    @BindView(R.id.impact_summary_edittext)
    EditText impactSummary;
    @BindView(R.id.project_role_layout)
    TextInputLayout roleLayout;
    @BindView(R.id.contributions_layout)
    TextInputLayout contributionLayout;
    @BindView(R.id.missed_opportunities)
    TextInputLayout missedOppLayout;
    @BindView(R.id.rec_rec_layout)
    TextInputLayout recReceivedLayout;
    @BindView(R.id.training_layout)
    TextInputLayout traininglayout;
    @BindView(R.id.assesting_others_layout)
    TextInputLayout assestingOtherLayout;
    @BindView(R.id.others_layout)
    TextInputLayout otherLayout;


    @BindView(R.id.project_role_edittext)
    EditText projectEdittext;
    @BindView(R.id.contributions_edittext)
    EditText contributionEdittext;
    @BindView(R.id.missed_opportunities_editText)
    EditText missedOppEdittext;
    @BindView(R.id.rec_rec_edittext)
    EditText recEditTect;
    @BindView(R.id.assesting_others_edittext)
    EditText assestingEditext;
    @BindView(R.id.training_edittext)
    EditText trainingEditext;
    @BindView(R.id.others_edittext)
    EditText othersEditText;

    @BindView(R.id.submit)
    Button submit;

    @OnClick(R.id.submit)
    public void onSubmit() {
        /*Log.d("testing===", String.valueOf(getDataFromForms()));*/
        // dummy values for impact==============================
        mapTest.put("inclusion","always");
        mapTest.put("metal","always");
        mapTest.put("pioneering","always");
        mapTest.put("accountable","always");
        mapTest.put("collabrative","always");
        mapTest.put("trust","always");



        //=================================
        mapTest.put("rolesUnderstand",projectEdittext.getText().toString());
        mapTest.put("contribution",contributionEdittext.getText().toString());
        mapTest.put("missedOpportunity",missedOppEdittext.getText().toString());
        mapTest.put("appreciation",recEditTect.getText().toString());
        mapTest.put("training",trainingEditext.getText().toString());
        mapTest.put("helpAnyone",assestingEditext.getText().toString());
        mapTest.put("others",othersEditText.getText().toString());
        Log.d("testing===", String.valueOf(mapTest));
        submitFormDetails(mapTest);

    }

    private void submitFormDetails(HashMap<String, Object> mapTest) {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails",
                MODE_PRIVATE);
        int userId=sharedPreferences.getInt("userId",-1);
        Log.d("mapTest===", String.valueOf(mapTest));
        Call<AppraisalFormDataModel> retrofitCall= RestClient
                .getApplicationData()
                .sendAppraisalFormData(userId,mapTest);
        retrofitCall.enqueue(new Callback<AppraisalFormDataModel>() {
            @Override
            public void onResponse(Call<AppraisalFormDataModel> call, Response<AppraisalFormDataModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AppraisalFormActivity.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AppraisalFormActivity.this, "Something went wrong!!"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppraisalFormDataModel> call, Throwable t) {
                Toast.makeText(AppraisalFormActivity.this, "Something went wrong!!"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    int reporteeFlag;

    AppraisalAdapter appraisalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraisal_form);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getBundleData();

        appraisalAdapter = new AppraisalAdapter(this, reporteeFlag, new ImpactInterface() {
            @Override
            public void sendTextDataToActivity(String data,HashMap<String,Object> map) {
               Log.d("","");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(appraisalAdapter);


    }

   /* private HashMap<String,Object>  getDataFromForms() {
        if (!impactSummary.getText().toString().isEmpty()){
            map.put("1",impactSummary.getText().toString());
        }else if (!projectEdittext.getText().toString().isEmpty()){
            map.put("2",projectEdittext.getText().toString());
        }else if (!contributionEdittext.getText().toString().isEmpty()){
            map.put("3",contributionEdittext.getText().toString());
        }else if (!missedOppEdittext.getText().toString().isEmpty()){
            map.put("4",missedOppEdittext.getText().toString());
        }else if (!recEditTect.getText().toString().isEmpty()){
            map.put("5",recEditTect.getText().toString());
        }else if (!trainingEditext.getText().toString().isEmpty()){
            map.put("6",trainingEditext.getText().toString());
        }else if (!assestingEditext.getText().toString().isEmpty()){
            map.put("7",assestingEditext.getText().toString());
        }else if (!othersEditText.getText().toString().isEmpty()){
            map.put("8",othersEditText.getText().toString());
        }
        return map;
    }*/

    private void getBundleData() {
        reporteeFlag = getIntent().getIntExtra("reporteesFlag", -1);
    }

}
