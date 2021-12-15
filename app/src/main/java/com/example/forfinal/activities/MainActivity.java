package com.example.forfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forfinal.R;
import com.example.forfinal.interfaces.EmployeeDao;
import com.example.forfinal.models.AppDatabase;
import com.example.forfinal.models.Employee;
import com.example.forfinal.models.TimePeriod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmployeeDao employeeDao = AppDatabase.getDb(this).getEmployeeDao();
        employee = employeeDao.GetEmployee("Selim@selim.com");
        if (employee == null) {
            employeeDao.Insert(new Employee("Selim", "Selim@selim.com"));
            employee = employeeDao.GetEmployee("1");
        }

        Button button_SignIn = findViewById(R.id.button_SignIn);
        button_SignIn.setOnClickListener((View view) -> {

            Intent intent = new Intent(getApplicationContext(), TimePeriodActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("EmployeeId", String.valueOf(employee.getId()));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private List<Employee> ReadCSVEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.employees);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvEmployeeLine = reader.readLine(); //header line is read out before entering the while loop
            //String csvStudentLine;
            while ((csvEmployeeLine = reader.readLine()) != null) {
                String[] eachEmpLine = csvEmployeeLine.split(",");
                Employee eachEmployee = new Employee(eachEmpLine[0], "");
                employeeList.add(eachEmployee);
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

        return employeeList;
    }
}