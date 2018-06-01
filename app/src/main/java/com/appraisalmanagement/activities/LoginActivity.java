package com.appraisalmanagement.activities;

import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appraisalmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

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
    public void loginFunctionality(){
        if (validations()){
            submitApiCall();
        }
    }

    private void submitApiCall() {
        /*Call<>*/
    }

    private boolean validations() {
    if (mEmailEditText.getText().toString().isEmpty()){
        mEmailLayout.setError("Email field cannot be empty");
        return false;
    }else if (mPasswordEditText.getText().toString().isEmpty()){
        Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
        return false;
    }else if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString()).matches()){
        mEmailLayout.setError("Invalid format");
        return false;
    }else{
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
