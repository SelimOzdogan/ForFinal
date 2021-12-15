package com.example.forfinal.interfaces;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forfinal.models.Employee;
import com.example.forfinal.models.TimePeriod;

import java.util.List;

@Dao
public interface TimePeriodDao {

    @Insert(onConflict = REPLACE)
    void Insert(TimePeriod timePeriod);

    @Insert(onConflict = REPLACE)
    void Insert(List<TimePeriod> timePeriodList);

    @Update(onConflict = REPLACE)
    void Update(TimePeriod timePeriod);


    @Query("Select * From Timeperiods")
    List<TimePeriod> GetAll();

    @Query("Select * From Timeperiods where employeeid = :employeeid")
    List<TimePeriod> GetAllbyEmployee(String employeeid);

    @Query("Select * From Timeperiods where employeeid = :employeeid and periodid = :periodid")
    TimePeriod GetTimePeriod(String employeeid, String periodid);

    @Query("Select * From timeperiods where employeeid = :employeeid and periodid like :year")
    List<TimePeriod> GetTimePeriods(String employeeid, String year);

    @Query("Select periodid From timeperiods where employeeid = :employeeid")
    List<String> GetYears(String employeeid);
}
