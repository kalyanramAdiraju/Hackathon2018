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
import com.appraisalmanagement.activities.currentAppraisal.AppraisalFormActivity;
import com.appraisalmanagement.models.ReportessDataModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nineleaps on 2/6/18.
 */

public class MyReporteesAdapter extends RecyclerView.Adapter<MyReporteesAdapter.ViewHolderClass> {
    private Context context;
    private LayoutInflater layoutInflater;
    SendReporteeDataInterface sendReporteeDataInterface;
    View view;
    public List<ReportessDataModel> reporteesData;

    public MyReporteesAdapter(Context context, List<ReportessDataModel> reporteesData,
                              SendReporteeDataInterface sendReporteeDataInterface) {
        this.context = context;
        this.reporteesData = reporteesData;
        this.sendReporteeDataInterface = sendReporteeDataInterface;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.reportess_layout, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        if (!reporteesData.isEmpty()) {
            if (!"".equals(reporteesData.get(position).getEmployeeName())) {
                holder.name.setText(reporteesData.get(position).getEmployeeName());
                sendReporteeDataInterface.sendUserIdToActivity(reporteesData.get(position).getUserId());
            }

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,AppraisalFormActivity.class);
                    intent.putExtra("reporteeFlag",1);
                    intent.putExtra("userId",reporteesData.get(position).getUserId());
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return reporteesData.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        public ViewHolderClass(View itemView) {
            super(itemView);
            ButterKnife.bind(this, view);
        }
    }
}
