package com.example.forfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.forfinal.R;
import com.example.forfinal.adapters.TimePeriodAdapter;
import com.example.forfinal.interfaces.TimePeriodDao;
import com.example.forfinal.models.AppDatabase;
import com.example.forfinal.models.Employee;
import com.example.forfinal.models.TimePeriod;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimePeriodActivity extends AppCompatActivity {
    AppDatabase database;
    TimePeriodAdapter timePeriodAdapter;
    List<TimePeriod> TimePeriodList = new ArrayList<TimePeriod>();
    String EmployeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_period);

        EmployeeId = getIntent().getExtras().getString("EmployeeId");

        getPeriods("2021");
        checkCurrentPeriod("2021");

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView_PeriodList = findViewById(R.id.recyclerView_PeriodList);
        TimePeriodAdapter periodListAdapter = new TimePeriodAdapter(TimePeriodList, EmployeeId, new TimePeriodAdapter.itemClicked() {
            @Override
            public void clicked(int i) {
                Intent intent = new Intent(getApplicationContext(), ShiftActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EmployeeId", String.valueOf(EmployeeId));
                bundle.putString("PeriodId", String.valueOf(TimePeriodList.get(i).getPeriodId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView_PeriodList.setLayoutManager(lm);
        recyclerView_PeriodList.setAdapter(periodListAdapter);


//        recycleViewMainActivityTask.addItemDecoration(dividerItemDecoration);
//        recycleViewMainActivityTask.setLayoutManager(lm2);
//        recycleViewMainActivityTask.setAdapter(taskAdapter);


//        TimePeriodDao timePeriodDao = AppDatabase.getDb(this).getTimePeriodDao();
//        List<String> years = timePeriodDao.GetYears("1");
//        List<String> years = new ArrayList<String>();
//        if (!years.contains("2021"))
//            years.add("2021");
//        Spinner spinner_PeriodYears = findViewById(R.id.spinner_PeriodYears);
//        ArrayAdapter<String> spinner_dataAdapter = new ArrayAdapter<String>(this, R.layout.layout_spinner, years);
//        spinner_dataAdapter.setDropDownViewResource(R.layout.layout_spinner);
//        spinner_PeriodYears.setAdapter(spinner_dataAdapter);

//        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "TimeShifts.db").allowMainThreadQueries().build();
//        MyApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").allowMainThreadQueries().build()

        Spinner spinner_PeriodYears = findViewById(R.id.spinner_PeriodYears);
        spinner_PeriodYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSortBy = spinner_PeriodYears.getSelectedItem().toString();
                TimePeriodList = AppDatabase.getDb(getApplicationContext()).getTimePeriodDao().GetTimePeriods(EmployeeId, "2021%");
                periodListAdapter.TimePeriodList = TimePeriodList;
                periodListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void checkCurrentPeriod(String year) {
        String currentPeriod = year + "12/1";
        TimePeriodDao timePeriodDao = AppDatabase.getDb(getApplicationContext()).getTimePeriodDao();
        if (timePeriodDao.GetTimePeriod(EmployeeId, currentPeriod) == null) {
            try {
                timePeriodDao.Insert(new TimePeriod(currentPeriod, EmployeeId));
            } catch (Exception ex) {
                Log.d("Selim", ex.toString());
            }
        }
    }

    private List<String> ReadCSVYear() {
        List<String> yearList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.employees);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvYearLine = reader.readLine();

            while ((csvYearLine = reader.readLine()) != null) {
                String[] eachYearLine = csvYearLine.split(",");
                yearList.add(eachYearLine[0]);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading csv file " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException("Error closing input stream " + ex);
            }
        }

        return yearList;
    }

    private void getPeriods(String year) {

        TimePeriodList = AppDatabase.getDb(this).getTimePeriodDao().GetTimePeriods(EmployeeId, year + "%");
    }
}