package com.harsh.sainih.wirecampdemo1.model;

import java.util.ArrayList;

/**
 * Created by sainih on 11/1/2017.
 */

/**
 * Object for the api Response from the API
 */

public class ApiResponse {
    private CityModel city;
    private int cnt;
    private ArrayList<DayWeatherModel> list;
    private Throwable throwable;

    public ApiResponse(CityModel city, int cnt, ArrayList<DayWeatherModel> list) {
        this.city = city;
        this.cnt = cnt;
        this.list = list;
        this.throwable = null;
    }

    public ApiResponse(Throwable throwable){
        this.throwable = throwable;
    }

    public CityModel getCity() {
        return city;
    }

    public int getCnt() {
        return cnt;
    }

    public ArrayList<DayWeatherModel> getList() {
        return list;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setList(ArrayList<DayWeatherModel> list) {
        this.list = list;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
