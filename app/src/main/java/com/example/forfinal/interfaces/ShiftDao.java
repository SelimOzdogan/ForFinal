package com.example.forfinal.interfaces;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forfinal.models.Employee;
import com.example.forfinal.models.Shift;

import java.util.List;

@Dao
public interface ShiftDao {
    @Insert(onConflict = REPLACE)
    void Insert(Shift shift);

    @Insert(onConflict = REPLACE)
    void Insert(List<Shift> shiftList);

    @Update(onConflict = REPLACE)
    void Update(List<Shift> shiftList);

    @Query("Select * From Shifts")
    List<Shift> GetAll();

    @Query("Select * From Shifts where employeeid = :employeeid and periodid = :periodid")
    List<Shift> GetShift(String employeeid, String periodid);
}
