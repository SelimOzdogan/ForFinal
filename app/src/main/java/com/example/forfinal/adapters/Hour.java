package com.example.forfinal.adapters;


import android.media.session.MediaSessionManager;

import java.util.ArrayList;
import java.util.List;

public class Hour {
    private static List<Hour> Hour_List;
    private int id;
    private double value;
    static ArrayList<String> names;

    public Hour(int id, double value) {
        this.id = id;
        this.value = value;
    }

    public static void initHour() {
        Hour_List = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Hour_List.add(new Hour(i, i));
            Hour_List.add(new Hour(i, i + 0.5));
        }
        Hour_List.add(new Hour(8, 8));
    }

    public static ArrayList<String> hourNames() {
        names = new ArrayList<>();
        for (int i = 0; i < Hour_List.size(); i++) {
            names.add(String.valueOf(Hour_List.get(i).value));
        }
        return names;
    }

    public static ArrayList<String> getNames() {
        return names;
    }

    public static List<Hour> getHour_List() {
        return Hour_List;
    }

    public static void setHour_List(List<Hour> hour_List) {
        Hour_List = hour_List;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
