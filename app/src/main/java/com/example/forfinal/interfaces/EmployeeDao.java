package com.example.forfinal.interfaces;

import static androidx.room.OnConflictStrategy.REPLACE;

import com.example.forfinal.models.Employee;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert(onConflict = REPLACE)
    void Insert(Employee employee);

    @Insert(onConflict = REPLACE)
    void Insert(List<Employee> employeeList);

    @Query("Select * From Employees")
    List<Employee> GetAll();

    @Query("Select * From Employees where employeeid = :employeeid")
    List<Employee> GetEmployees(String employeeid);

    @Query("Select * From Employees where employeeid = :employeeid")
    Employee GetEmployee(String employeeid);
}
