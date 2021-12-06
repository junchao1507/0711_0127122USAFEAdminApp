package com.example.a0711_0127122usafeadminapp;

import java.util.ArrayList;

public class SpinnerItem {
    private static ArrayList<SpinnerItem> days = new ArrayList<>();
    private static ArrayList<SpinnerItem> weeks = new ArrayList<>();

    private String name;

    public SpinnerItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void dayList(){
        days.add(new SpinnerItem("Monday"));
        days.add(new SpinnerItem("Tuesday"));
        days.add(new SpinnerItem("Wednesday"));
        days.add(new SpinnerItem("Thursday"));
        days.add(new SpinnerItem("Friday"));
    }

    public static void weekList(){
        weeks.add(new SpinnerItem("1"));
        weeks.add(new SpinnerItem("2"));
        weeks.add(new SpinnerItem("3"));
        weeks.add(new SpinnerItem("4"));
        weeks.add(new SpinnerItem("5"));
        weeks.add(new SpinnerItem("6"));
        weeks.add(new SpinnerItem("7"));
        weeks.add(new SpinnerItem("8"));
        weeks.add(new SpinnerItem("9"));
        weeks.add(new SpinnerItem("10"));
        weeks.add(new SpinnerItem("11"));
        weeks.add(new SpinnerItem("12"));
    }

    public static ArrayList<SpinnerItem> getDayList() {
        return days;
    }

    public static ArrayList<SpinnerItem> getWeekList() {
        return weeks;
    }
}
