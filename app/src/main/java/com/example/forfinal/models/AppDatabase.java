package com.example.forfinal.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.forfinal.interfaces.EmployeeDao;
import com.example.forfinal.interfaces.ShiftDao;
import com.example.forfinal.interfaces.TimePeriodDao;

@Database(entities = {Employee.class, TimePeriod.class, Shift.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao getEmployeeDao();
    public abstract ShiftDao getShiftDao();
    public abstract TimePeriodDao getTimePeriodDao();

    static AppDatabase db;

    public static AppDatabase getDb(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, "TimeShifts.db").allowMainThreadQueries().build();
        }
        return db;
    }
}
