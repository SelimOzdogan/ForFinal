package com.example.forfinal.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "timeperiods", primaryKeys = {"periodid", "employeeid"},
        foreignKeys = @ForeignKey(entity = Employee.class,
                parentColumns = "employeeid", childColumns = "employeeid",
                onDelete = CASCADE))
public class TimePeriod {
    @ColumnInfo(name = "periodid")
    @NonNull
    private String PeriodId;

    @ColumnInfo(name = "employeeid")
    @NonNull
    private String EmployeeId;

    @ColumnInfo(name = "totalhours")
    private double TotalHours;

    @ColumnInfo(name = "stathours")
    private double StatHours;

    public TimePeriod() {
        PeriodId = "";
        EmployeeId = "";
        TotalHours = 0.0;
        StatHours = 0.0;
    }

    public TimePeriod(@NonNull String periodId, @NonNull String employeeId) {
        PeriodId = periodId;
        EmployeeId = employeeId;
        TotalHours = 0.0;
        StatHours = 0.0;
    }

    public TimePeriod(@NonNull String periodId, @NonNull String employeeId, double totalHours, double statHours) {
        PeriodId = periodId;
        EmployeeId = employeeId;
        TotalHours = totalHours;
        StatHours = statHours;
    }

    @NonNull
    public String getPeriodId() {
        return PeriodId;
    }

    public void setPeriodId(@NonNull String periodId) {
        PeriodId = periodId;
    }

    @NonNull
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(@NonNull String employeeId) {
        EmployeeId = employeeId;
    }

    public double getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(double totalHours) {
        TotalHours = totalHours;
    }

    public double getStatHours() {
        return StatHours;
    }

    public void setStatHours(double statHours) {
        StatHours = statHours;
    }
}
