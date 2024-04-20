package com.example.coffeeappcs372final;

public class BrewModel {
    private int id;
    private String beans;
    private String brewer;
    private float grams;
    private float water;
    private float temp;
    private String method;
    private String time;

    public BrewModel(int id, String beans, String brewer, float grams, float water, float temp, String method, String time) {
        this.id = id;
        this.beans = beans;
        this.brewer = brewer;
        this.grams = grams;
        this.water = water;
        this.temp = temp;
        this.method = method;
        this.time = time;
    }

    @Override
    public String toString() {
        return "BrewModel{" +
                "id=" + id +
                ", beans='" + beans + '\'' +
                ", brewer='" + brewer + '\'' +
                ", grams=" + grams +
                ", water=" + water +
                ", temp=" + temp +
                ", method='" + method + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeans() {
        return beans;
    }

    public void setBeans(String beans) {
        this.beans = beans;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTime() { return time;}
}

