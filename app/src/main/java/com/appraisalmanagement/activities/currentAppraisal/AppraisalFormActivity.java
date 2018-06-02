package com.appraisalmanagement.activities.currentAppraisal;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.adapters.AppraisalAdapter;
import com.appraisalmanagement.adapters.ImpactInterface;
import com.appraisalmanagement.models.AppraisalFormDataModel;
import com.appraisalmanagement.models.GetAppraisalFormDataModel;
import com.appraisalmanagement.models.GetAppraisalFormDataModelData;
import com.appraisalmanagement.network.RestClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppraisalFormActivity extends AppCompatActivity {
    HashMap<String, Object> mapTest = new HashMap<>();
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
        if (isEditable){
            submit.setVisibility(View.VISIBLE);
            mapTest.put("inclusion", "always");
            mapTest.put("metal", "always");
            mapTest.put("pioneering", "always");
            mapTest.put("accountable", "always");
            mapTest.put("collabrative", "always");
            mapTest.put("trust", "always");


            //=================================
            mapTest.put("rolesUnderstand", projectEdittext.getText().toString());
            mapTest.put("contribution", contributionEdittext.getText().toString());
            mapTest.put("missedOpportunity", missedOppEdittext.getText().toString());
            mapTest.put("appreciation", recEditTect.getText().toString());
            mapTest.put("training", trainingEditext.getText().toString());
            mapTest.put("helpAnyone", assestingEditext.getText().toString());
            mapTest.put("others", othersEditText.getText().toString());
            Log.d("testing===", String.valueOf(mapTest));
            submitFormDetails(mapTest);
        }else {
            submit.setVisibility(View.GONE);
        }
        /*Log.d("testing===", String.valueOf(getDataFromForms()));*/
        // dummy values for impact==============================


    }

    private void submitFormDetails(HashMap<String, Object> mapTest) {
        Log.d("mapTest===", String.valueOf(mapTest));
        Call<AppraisalFormDataModel> retrofitCall = RestClient
                .getApplicationData()
                .sendAppraisalFormData(userId, mapTest);
        retrofitCall.enqueue(new Callback<AppraisalFormDataModel>() {
            @Override
            public void onResponse(Call<AppraisalFormDataModel> call, Response<AppraisalFormDataModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AppraisalFormActivity.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AppraisalFormActivity.this, "Something went wrong!!" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppraisalFormDataModel> call, Throwable t) {
                Toast.makeText(AppraisalFormActivity.this, "Something went wrong!!" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    int reporteeFlag;

    AppraisalAdapter appraisalAdapter;
    int userId;
    boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraisal_form);
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails",
                MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        isEditable = sharedPreferences.getBoolean("isEditable", false);
        if (!isEditable){
            getFormDataFromServer();
        }
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getBundleData();

        appraisalAdapter = new AppraisalAdapter(this, reporteeFlag, new ImpactInterface() {
            @Override
            public void sendTextDataToActivity(String data, HashMap<String, Object> map) {
                Log.d("", "");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(appraisalAdapter);
    }

    private void getFormDataFromServer() {
        final GetAppraisalFormDataModelData getAppraisalFormDataModelData = new GetAppraisalFormDataModelData();

        Call<GetAppraisalFormDataModel> retrofitCall = RestClient
                .getApplicationData()
                .getAppraisalFormData(userId);
        retrofitCall.enqueue(new Callback<GetAppraisalFormDataModel>() {
            @Override
            public void onResponse(Call<GetAppraisalFormDataModel> call, Response<GetAppraisalFormDataModel> response) {
                if (response.isSuccessful()) {
                    if (null != response.body().getData()) {
                        getAppraisalFormDataModelData.setAccountable(response.body().getData().getAccountable());
                        getAppraisalFormDataModelData.setAppreciation(response.body().getData().getAppreciation());
                        getAppraisalFormDataModelData.setCollabrative(response.body().getData().getCollabrative());
                        getAppraisalFormDataModelData.setContribution(response.body().getData().getContribution());
                        getAppraisalFormDataModelData.setHelpAnyone(response.body().getData().getHelpAnyone());
                        getAppraisalFormDataModelData.setInclusion(response.body().getData().getInclusion());
                        getAppraisalFormDataModelData.setMetal(response.body().getData().getMetal());
                        getAppraisalFormDataModelData.setMissedOpportunity(response.body().getData().getMissedOpportunity());
                        getAppraisalFormDataModelData.setOthers(response.body().getData().getOthers());
                        getAppraisalFormDataModelData.setPioneering(response.body().getData().getPioneering());
                        getAppraisalFormDataModelData.setRolesUnderstand(response.body().getData().getRolesUnderstand());
                        getAppraisalFormDataModelData.setTraining(response.body().getData().getTraining());
                        getAppraisalFormDataModelData.setTrust(response.body().getData().getTrust());
                        setUpProfile(getAppraisalFormDataModelData);
                    }

                } else {
                    Toast.makeText(AppraisalFormActivity.this, "some thing went wrong" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAppraisalFormDataModel> call, Throwable t) {
                Toast.makeText(AppraisalFormActivity.this, "some thing went wrong" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpProfile(GetAppraisalFormDataModelData getAppraisalFormDataModelData) {
    if (null!=getAppraisalFormDataModelData){
        projectEdittext.setText(getAppraisalFormDataModelData.getRolesUnderstand());
        projectEdittext.setEnabled(false);
        contributionEdittext.setText(getAppraisalFormDataModelData.getContribution());
        contributionEdittext.setEnabled(false);
        missedOppEdittext.setText(getAppraisalFormDataModelData.getMissedOpportunity());
        missedOppEdittext.setEnabled(false);
        recEditTect.setEnabled(false);
        trainingEditext.setEnabled(false);
        trainingEditext.setText(getAppraisalFormDataModelData.getTraining());
        othersEditText.setText(getAppraisalFormDataModelData.getOthers());
        othersEditText.setEnabled(false);
        impactSummary.setEnabled(false);
        submit.setEnabled(false);
        assestingEditext.setEnabled(false);
        submit.setVisibility(View.GONE);
    }

    }

    private void getBundleData() {
        reporteeFlag = getIntent().getIntExtra("reporteesFlag", -1);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
