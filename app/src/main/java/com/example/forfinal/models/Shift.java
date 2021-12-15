package com.example.forfinal.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "shifts", primaryKeys = {"periodid", "employeeid", "shiftid"},
        foreignKeys = @ForeignKey(entity = TimePeriod.class,
                parentColumns = {"employeeid","periodid"}, childColumns = {"employeeid","periodid"},
                onDelete = CASCADE))
public class Shift {
    @ColumnInfo(name = "shiftid")
    @NonNull
    private String Shiftid;

    @ColumnInfo(name = "employeeid")
    @NonNull
    private String EmployeeId;

    @ColumnInfo(name = "periodid")
    @NonNull
    private String PeriodId;

    @ColumnInfo(name = "hours")
    private double TotalHours;

    @ColumnInfo(name = "stathours")
    private double StatHours;

    public Shift() {
        Shiftid = "";
        EmployeeId = "";
        PeriodId = "";
        TotalHours = 0.0;
        StatHours = 0.0;
    }

    public Shift(@NonNull String shiftid, @NonNull String employeeId, @NonNull String periodId) {
        Shiftid = shiftid;
        EmployeeId = employeeId;
        PeriodId = periodId;
        TotalHours = 0.0;
        StatHours = 0.0;
    }

    public Shift(@NonNull String shiftid, @NonNull String employeeId, @NonNull String periodId, double totalHours, double statHours) {
        Shiftid = shiftid;
        EmployeeId = employeeId;
        PeriodId = periodId;
        TotalHours = totalHours;
        StatHours = statHours;
    }

    @NonNull
    public String getShiftid() {
        return Shiftid;
    }

    public void setShiftid(@NonNull String shiftid) {
        Shiftid = shiftid;
    }

    @NonNull
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(@NonNull String employeeId) {
        EmployeeId = employeeId;
    }

    @NonNull
    public String getPeriodId() {
        return PeriodId;
    }

    public void setPeriodId(@NonNull String periodId) {
        PeriodId = periodId;
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
