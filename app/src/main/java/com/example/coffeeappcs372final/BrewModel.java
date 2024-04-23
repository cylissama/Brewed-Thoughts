package com.example.coffeeappcs372final;

import androidx.annotation.NonNull;

public class BrewModel {
    private int id;
    private String beans;
    private String brewer;
    private String method;
    private final String time;
    private String note;
    private float grams;
    private float water;
    private float temp;
    private Integer favorite;
    // 0 = not, 1 = favorite
    // this is done bc SQLite had no native Boolean storage


    public BrewModel(int id, String beans, String brewer, float grams, float water, float temp, String method, String time, String note, Integer favorite) {
        this.id = id;
        this.beans = beans;
        this.brewer = brewer;
        this.grams = grams;
        this.water = water;
        this.temp = temp;
        this.method = method;
        this.time = time;
        this.favorite = favorite;
        this.note = note;
    }
    public BrewModel(int id, String beans, String brewer, float grams, float water, float temp, String method, String time) {
        this.id = id;
        this.beans = beans;
        this.brewer = brewer;
        this.grams = grams;
        this.water = water;
        this.temp = temp;
        this.method = method;
        this.time = time;
        this.favorite = 0;
        this.note = null;
    }

    @NonNull
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }
}

