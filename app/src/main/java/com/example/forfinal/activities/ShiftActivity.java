package com.example.forfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.forfinal.R;
import com.example.forfinal.adapters.ShiftAdapter;
import com.example.forfinal.adapters.TimePeriodAdapter;
import com.example.forfinal.interfaces.ShiftDao;
import com.example.forfinal.interfaces.TimePeriodDao;
import com.example.forfinal.models.AppDatabase;
import com.example.forfinal.models.Employee;
import com.example.forfinal.models.Shift;
import com.example.forfinal.models.TimePeriod;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ShiftActivity extends AppCompatActivity {

    String EmployeeId;
    String PeriodId;
    TimePeriod timePeriod;
    List<Shift> ShiftList = new ArrayList<Shift>();
    ShiftAdapter.ScrollChange scrollChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        EmployeeId = getIntent().getExtras().getString("EmployeeId");
        PeriodId = getIntent().getExtras().getString("PeriodId");

        checkCurrentPeriodShifts();
        getTimePeriod();
        getPeriodShifts();
        RecyclerView recyclerview_ShiftDays = findViewById(R.id.recyclerview_ShiftDays);
        ShiftAdapter shiftAdapter = new ShiftAdapter(ShiftList, new ShiftAdapter.ScrollChange() {
            @Override
            public void change(List<Shift> shiftList) {
                ShiftList = shiftList;
                TextView textView_ShiftHours = findViewById(R.id.textView_ShiftHours);
                textView_ShiftHours.setText(String.valueOf(CalculateTotalHours()));
            }
        });

        GridLayoutManager gm = new GridLayoutManager(this, 3);
        recyclerview_ShiftDays.setLayoutManager(gm);
        recyclerview_ShiftDays.setAdapter(shiftAdapter);


        TextView textView_ShiftInfo = findViewById(R.id.textView_ShiftInfo);
        textView_ShiftInfo.setText(PeriodId);

        TextView textView_ShiftHours = findViewById(R.id.textView_ShiftHours);
        textView_ShiftHours.setText(String.valueOf(CalculateTotalHours()));

        Button button_ShiftSave = findViewById(R.id.button_ShiftSave);
        button_ShiftSave.setOnClickListener((View view) -> {
            AppDatabase.getDb(this).getShiftDao().Update(ShiftList);
            AppDatabase.getDb(this).getTimePeriodDao().Update(timePeriod);

            Intent intent = new Intent(getApplicationContext(), TimePeriodActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("EmployeeId", String.valueOf(EmployeeId));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button button_Send = findViewById(R.id.button_Send);
        button_Send.setOnClickListener((View view) -> {
            SendEmail();
        });
    }

    private void checkCurrentPeriodShifts() {
        ShiftDao shiftDao = AppDatabase.getDb(getApplicationContext()).getShiftDao();
        if (shiftDao.GetShift(EmployeeId, PeriodId).size() <= 0) {
            try {
                Calendar calc;
                int year = Integer.parseInt(PeriodId.substring(0, 4));
                int month = Integer.parseInt(PeriodId.substring(4, 6));
                boolean First = (Integer.parseInt(PeriodId.substring(7, 8)) == 1 ? true : false);
                for (int i = (First ? 1 : 16); i < (First ? 16 : 32); i++) {
                    calc = new GregorianCalendar(year, month - 1, i);
                    if (calc.get(Calendar.MONTH) + 1 == month) {
                        String shiftId = new SimpleDateFormat("yyyyMMdd").format(calc.getTime());
                        ShiftList.add(new Shift(shiftId, EmployeeId, PeriodId));
                    }
                }
                shiftDao.Insert(ShiftList);
            } catch (Exception ex) {
                Log.d("Selim", ex.toString());
            }
        }
    }


    private Employee getEmployee() {
        return AppDatabase.getDb(this).getEmployeeDao().GetEmployee(EmployeeId);
    }

    private void getPeriodShifts() {
        ShiftList = AppDatabase.getDb(this).getShiftDao().GetShift(EmployeeId, PeriodId);
    }

    private void getTimePeriod() {
        timePeriod = AppDatabase.getDb(this).getTimePeriodDao().GetTimePeriod(EmployeeId, PeriodId);
    }

    private double CalculateTotalHours() {
        double hours = 0;
        for (Shift shift : ShiftList) {
            hours += shift.getTotalHours();
        }
        timePeriod.setTotalHours(hours);
        return hours;
    }

    private void SendEmail() {
        Log.i("Send email", "");

        String[] TO = {"bloglarinfo@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getEmployee().getName()+" - " +PeriodId );
        emailIntent.putExtra(Intent.EXTRA_TEXT, getMessage());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShiftActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getMessage() {
        String output="";
        for (Shift shift:ShiftList             ) {
            output +=String.format( "%s %2.2f h\n",shift.getShiftid(), shift.getTotalHours());
        }
        return output;
    }
}