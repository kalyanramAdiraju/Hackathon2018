package com.appraisalmanagement.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appraisalmanagement.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by nineleaps on 1/6/18.
 */

public class AppraisalAdapter extends RecyclerView.Adapter<AppraisalAdapter.AppraisalViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    HashMap<String,Object> mapData=new HashMap<>();
    int reporteeFlag;
    ImpactInterface impactInterface;
    int positionItem=-1;
    private String[] itemsList= {"Never", "Rarely", "Sometimes", "Mostly","Always"};
    private String[] impactArray = {"I", "M", "P", "A", "C", "T"};


    public AppraisalAdapter(Context context, int reporteeFlag,ImpactInterface impactInterface) {
        this.context = context;
        this.reporteeFlag = reporteeFlag;
        this.impactInterface=impactInterface;
    }

    @NonNull
    @Override
    public AppraisalAdapter.AppraisalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.appraisal_layout, parent, false);
        return new AppraisalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppraisalAdapter.AppraisalViewHolder holder, int position) {
        holder.textView.setText(impactArray[position]);
        Log.d("reporteeFlag==", String.valueOf(reporteeFlag));
        if (-1!=reporteeFlag){
            Log.d("hello3","1");
            holder.managerTextView.setOnClickListener(getSingleSelectedPopUp(holder,2,position));
            impactInterface.sendTextDataToActivity(holder.managerTextView.getText().toString(),mapData);
            holder.selfTextView.setOnClickListener(null);
            holder.selfTextView.setTextColor(Color.parseColor("#c0c0c0"));
        }else {
            Log.d("hello3","2");
            holder.managerTextView.setOnClickListener(null);
            holder.selfTextView.setOnClickListener(getSingleSelectedPopUp(holder,1,position));
            impactInterface.sendTextDataToActivity(holder.selfTextView.getText().toString(),mapData);
            holder.managerTextView.setTextColor(Color.parseColor("#c0c0c0"));
        }
    }

    private View.OnClickListener getSingleSelectedPopUp(final AppraisalViewHolder holder, final int flag,final int position) {
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Select items");
               builder.setSingleChoiceItems(
                       itemsList,
                       positionItem,
                       new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               positionItem = i;
                               if (flag==2){
                                   holder.managerTextView.setText(itemsList[i]);
                                   mapData.put(impactArray[i],itemsList[i]);
                               }else if (flag==1){
                                   holder.selfTextView.setText(itemsList[i]);
                                   mapData.put(impactArray[i],itemsList[i]);
                               }

                               dialogInterface.dismiss();
                           }
                       });
               builder.show();
           }
       };
    }

    @Override
    public int getItemCount() {
        return 6;
    }



    public class AppraisalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView textView;

        @BindView(R.id.self)
        TextView selfTextView;

        @BindView(R.id.manager)
        TextView managerTextView;

        public AppraisalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, view);
        }
    }
}
