package com.appraisalmanagement.activities;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appraisalmanagement.R;
import com.appraisalmanagement.adapters.DashboardRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.dashboard_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int role;

    DashboardRecyclerViewAdapter mDashboardRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setUpToolBar();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        getDataFromSharedPreferences();
        mDashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(this, role);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mDashboardRecyclerViewAdapter);
    }

    private void getDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        role=sharedPreferences.getInt("role",-1);

    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.action_menu == id) {
            openConformationPopUp(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void openConformationPopUp(final MenuItem item) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_goals);
        dialog.setCancelable(false);
        TextView reopenText = (TextView) dialog.findViewById(R.id.delete_text);
        reopenText.setText("");
        TextView batchName = (TextView) dialog.findViewById(R.id.goal_name);
        batchName.setText("Logout");

        final Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        cancel.setText("NO");
        final Button yes = (Button) dialog.findViewById(R.id.btn_yes);
        yes.setText("YES");
        item.setChecked(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item.setChecked(false);
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutApiCall();
                dialog.dismiss();
                finish();

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void logoutApiCall() {
        clearLocalData();
        redirectToLoginPage();
    }

    private void clearLocalData() {
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userDetails");
        editor.clear();
        editor.commit();
        Toast.makeText(this,
                "logged out Successfully", Toast.LENGTH_SHORT).show();
    }

    private void redirectToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


}
