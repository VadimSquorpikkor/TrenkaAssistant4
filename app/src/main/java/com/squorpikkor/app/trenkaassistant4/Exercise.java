package com.squorpikkor.app.trenkaassistant4;

public class Exercise {

    private int ID;


    private double weight_1;
    private int count_1;
    private double weight_2;
    private int count_2;
    private double weight_3;
    private int count_3;
    private double weight_4;
    private int count_4;

    private String name;
    private int date; //date как интегер, потому как date в этой таблице является Foreign key, сами данные в таблице Training
    private int status;

    //region getters & setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getWeight_1() {
        return weight_1;
    }

    public void setWeight_1(double weight_1) {
        this.weight_1 = weight_1;
    }

    public int getCount_1() {
        return count_1;
    }

    public void setCount_1(int count_1) {
        this.count_1 = count_1;
    }

    public double getWeight_2() {
        return weight_2;
    }

    public void setWeight_2(double weight_2) {
        this.weight_2 = weight_2;
    }

    public int getCount_2() {
        return count_2;
    }

    public void setCount_2(int count_2) {
        this.count_2 = count_2;
    }

    public double getWeight_3() {
        return weight_3;
    }

    public void setWeight_3(double weight_3) {
        this.weight_3 = weight_3;
    }

    public int getCount_3() {
        return count_3;
    }

    public void setCount_3(int count_3) {
        this.count_3 = count_3;
    }

    public double getWeight_4() {
        return weight_4;
    }

    public void setWeight_4(double weight_4) {
        this.weight_4 = weight_4;
    }

    public int getCount_4() {
        return count_4;
    }

    public void setCount_4(int count_4) {
        this.count_4 = count_4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    //endregion
}
