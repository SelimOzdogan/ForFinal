package com.example.forfinal.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfinal.activities.ShiftActivity;
import com.example.forfinal.activities.TimePeriodActivity;
import com.example.forfinal.models.TimePeriod;
import com.example.forfinal.R;

import java.util.List;

public class TimePeriodAdapter extends RecyclerView.Adapter<TimePeriodAdapter.TimePeriodHolder> {
    public List<TimePeriod> TimePeriodList;
    int currIndex = -1;
    itemClicked itemClicked;
    Toast toast;
    TimePeriodHolder timePeriodHolder;
    String EmployeeId;

    public TimePeriodAdapter(List<TimePeriod> timePeriodList, String employeeId, TimePeriodAdapter.itemClicked itemClicked) {
        TimePeriodList = timePeriodList;
        this.itemClicked = itemClicked;
        this.EmployeeId = employeeId;
    }


    @NonNull
    @Override
    public TimePeriodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View taskView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_timeperiod, parent, false);

        TimePeriodHolder taskHolder = new TimePeriodHolder(taskView);

        TextView textView_PeriodName = taskHolder.timePeriodItem.findViewById(R.id.textView_PeriodName);

        textView_PeriodName.setOnClickListener((View view) -> {
            itemClicked.clicked(taskHolder.getAdapterPosition());
//            new AlertDialog.Builder((parent.getContext()))
//                    .setTitle(textView_PeriodName.getText().toString() + " " + textView_PeriodName.getText().toString())
//                    .setMessage("Task Deadline: " + TimePeriodList.get(taskHolder.getAdapterPosition()).getDeadline()).show();

        });

        return taskHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimePeriodHolder holder, int position) {
        TextView textView_PeriodName = holder.timePeriodItem.findViewById(R.id.textView_PeriodName);
        TextView textView_PeriodStatHours = holder.timePeriodItem.findViewById(R.id.textView_PeriodStatHours);
        TextView textView_PeriodTotalHours = holder.timePeriodItem.findViewById(R.id.textView_PeriodTotalHours);

        textView_PeriodName.setText(TimePeriodList.get(position).getPeriodId());
        textView_PeriodStatHours.setText(String.valueOf(TimePeriodList.get(position).getStatHours()));
        textView_PeriodTotalHours.setText(String.valueOf(TimePeriodList.get(position).getTotalHours()));


    }

    @Override
    public int getItemCount() {
        return TimePeriodList.size();
    }

    public class TimePeriodHolder extends RecyclerView.ViewHolder {

        View timePeriodItem;

        public TimePeriodHolder(@NonNull View itemView) {
            super(itemView);
            timePeriodItem = itemView;
        }
    }

    public interface itemClicked {

        public void clicked(int i);
    }
}
