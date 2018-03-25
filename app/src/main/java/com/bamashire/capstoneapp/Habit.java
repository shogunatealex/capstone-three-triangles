package com.bamashire.capstoneapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Habit {

    private String name;
    private String description;
    private int frequency;

    private int consecutiveDays;
    private ArrayList<Integer> checkInHistory;

    public Habit(String name) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;

        checkInHistory = new ArrayList<Integer>();
        consecutiveDays = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getConsecutiveDays() {
        return consecutiveDays;
    }

    public void incrementConsecutiveDays() {
        this.consecutiveDays += 1;
    }

    public ArrayList<Integer> getCheckInHistory() {
        return checkInHistory;
    }

    public void checkIn() {
        this.checkInHistory.add(1);
    }
}
