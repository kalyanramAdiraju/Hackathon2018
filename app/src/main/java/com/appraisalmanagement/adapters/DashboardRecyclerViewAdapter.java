package com.appraisalmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appraisalmanagement.R;
import com.appraisalmanagement.activities.MonthlyAssessmentActivity;
import com.appraisalmanagement.activities.MyReporteesActivity;
import com.appraisalmanagement.activities.PreviousAppraisalActivity;
import com.appraisalmanagement.activities.SummaryActivity;
import com.appraisalmanagement.activities.currentAppraisal.AppraisalFormActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nineleaps on 1/6/18.
 */

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<DashboardRecyclerViewAdapter.DashboardViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private View view;
    private int role;
    private String[] employeesArray={"Previous Appraisals","Current Appraisal","Assignments Feedback"};
    private String[] managersArray = {"Previous Appraisals","Current Appraisal","Assignments Feedback","Pending Appraisals"};
    private String[] hrArray = {"Previous Appraisals","Current Appraisal","Assignments Feedback","Pending Appraisals","Reports"};

    public DashboardRecyclerViewAdapter(Context mContext, int role) {
        this.role = role;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public DashboardRecyclerViewAdapter.DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.folder_view_dashboard, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DashboardRecyclerViewAdapter.DashboardViewHolder holder, int position) {
        if (role == 1) {
            holder.textView.setText(employeesArray[position]);
        }else if (role==2){
            holder.textView.setText(managersArray[position]);
        }else {
            holder.textView.setText(hrArray[position]);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Previous Appraisals".equals(holder.textView.getText().toString())){
                    mContext.startActivity(new Intent(mContext,PreviousAppraisalActivity.class));
                }else if ("Current Appraisal".equals(holder.textView.getText().toString())){
                    mContext.startActivity(new Intent(mContext,AppraisalFormActivity.class));
                }else if ("Assignments Feedback".equals(holder.textView.getText().toString())){
                    mContext.startActivity(new Intent(mContext,MonthlyAssessmentActivity.class));
                }else if ("Pending Appraisals".equals(holder.textView.getText().toString())){
                    mContext.startActivity(new Intent(mContext,MyReporteesActivity.class));
                }else if ("Reports".equals(holder.textView.getText().toString())){
                    mContext.startActivity(new Intent(mContext,SummaryActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return setUpRole();
    }

    private int setUpRole() {
        if (role == 1) {
            return 3;
        } else if (role == 2) {
            return 4;
        } else return 5;
    }


    public class DashboardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sample_text)
        TextView textView;

        public DashboardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,view);

        }
    }
}
