package com.squorpikkor.app.trenkaassistant4;

import java.util.ArrayList;

//todo Может "Workout" ?
public class Training {

    //--------------------------------------//
    //  Тренировка содержит все упражнения  //
    //  текущего дня                        //
    //--------------------------------------//

    private int year;
    private int month;
    private int day;
    private double userWeight;
    private int allExerciseCount;
    private int newRecordCount;

    private ArrayList<Exercise> exerciseList;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }

    public int getAllExerciseCount() {
        return allExerciseCount;
    }

    public void setAllExerciseCount(int allExerciseCount) {
        this.allExerciseCount = allExerciseCount;
    }

    public int getNewRecordCount() {
        return newRecordCount;
    }

    public void setNewRecordCount(int newRecordCount) {
        this.newRecordCount = newRecordCount;
    }

    public Training(int year, int month, int day, double userWeight, int allExerciseCount, int newRecordCount) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.userWeight = userWeight;
        this.allExerciseCount = allExerciseCount;
        this.newRecordCount = newRecordCount;
        this.exerciseList = new ArrayList<>();  //todo insert empty exercises into new list
    }
}
