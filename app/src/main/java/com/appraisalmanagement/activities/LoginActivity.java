package com.appraisalmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.models.LoginModel;
import com.appraisalmanagement.network.RestClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_edit_text)
    EditText mEmailEditText;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.email_layout)
    TextInputLayout mEmailLayout;

    @BindView(R.id.password_layout)
    TextInputLayout mPasswordLayout;

    @BindView(R.id.signIn)
    Button mSubmitButton;

    @OnClick(R.id.signIn)
    public void loginFunctionality() {
        if (validations()) {
            submitApiCall();
        }
    }

    private void submitApiCall() {
        Call<LoginModel> loginCall = RestClient
                .getApplicationData()
                .sendLoginInfoToServer(mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    saveDataInPreferences(response);
                    redirectToDashboard();
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    Log.e("response", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("response", String.valueOf(t));
            }
        });
    }

    private void saveDataInPreferences(Response<LoginModel> response) {
        SharedPreferences sp = getSharedPreferences("userDetails" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("role",response.body().getData().getRole());
        editor.putInt("userId",response.body().getData().getUserId());
        editor.putString("emailId",response.body().getData().getEmailId());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void redirectToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private boolean validations() {
        if (mEmailEditText.getText().toString().isEmpty()) {
            mEmailLayout.setError("Email field cannot be empty");
            return false;
        } else if (mPasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString()).matches()) {
            mEmailLayout.setError("Invalid format");
            return false;
        } else {
            mEmailLayout.setErrorEnabled(false);
            mPasswordLayout.setErrorEnabled(false);
            return true;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
