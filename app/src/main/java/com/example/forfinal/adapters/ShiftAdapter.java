package com.example.forfinal.adapters;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forfinal.R;
import com.example.forfinal.activities.ShiftActivity;
import com.example.forfinal.models.Shift;

import org.w3c.dom.ls.LSInput;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftHolder> {
    List<Shift> ShiftList;
    ScrollChange scrollChange;
    ShiftHolder taskHolder;

    public ShiftAdapter(List<Shift> shiftList, ScrollChange scrollChange) {
        ShiftList = shiftList;
        this.scrollChange = scrollChange;
    }

    @NonNull
    @Override
    public ShiftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shifts, parent, false);

        taskHolder = new ShiftAdapter.ShiftHolder(view);


        return taskHolder;
    }

    @Override
    public int getItemCount() {
        return ShiftList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftHolder holder, int position) {
        TextView textView_ShiftDay = holder.shiftItem.findViewById(R.id.textView_ShiftDay);
        TextView textView_ShiftMonth = holder.shiftItem.findViewById(R.id.textView_ShiftMonth);
        NumberPicker numberPicker_ShiftHour = holder.shiftItem.findViewById(R.id.numberPicker_ShiftHour);

        Hour.initHour();
        numberPicker_ShiftHour.setMaxValue(Hour.getHour_List().size() - 1);
        numberPicker_ShiftHour.setMinValue(0);
        numberPicker_ShiftHour.setDisplayedValues(Hour.hourNames().toArray(new String[0]));
        numberPicker_ShiftHour.setValue(Hour.getNames().indexOf(String.valueOf(ShiftList.get(position).getTotalHours())));
        numberPicker_ShiftHour.setTag(position);

        String Day = ShiftList.get(position).getShiftid().substring(6, 8);
        String Month = ShiftList.get(position).getShiftid().substring(4, 6);
        textView_ShiftDay.setText(Day);
        textView_ShiftMonth.setText("Dec");

        numberPicker_ShiftHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int position = (Integer) picker.getTag();
                ShiftList.get(position).setTotalHours(Double.parseDouble(Hour.hourNames().get(newVal)));
                scrollChange.change(ShiftList);
            }
        });

        numberPicker_ShiftHour.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                numberPicker.getParent();
//                scrollChange.change(CalculateTotalHours());
            }
        });
        numberPicker_ShiftHour.setOnScrollListener((NumberPicker numberPicker, int index) -> {

        });
    }

    public class ShiftHolder extends RecyclerView.ViewHolder {

        View shiftItem;

        public ShiftHolder(@NonNull View itemView) {
            super(itemView);
            shiftItem = itemView;
        }
    }

    public interface ScrollChange {

        public void change(List<Shift> shiftList);
    }
}

