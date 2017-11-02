package com.harsh.sainih.wirecampdemo1.model;

import com.google.gson.annotations.SerializedName;

/** tempModel
 * Created by sainih on 11/1/2017.
 */

public class TempModel {
//               "temp":{
//            "day":297.77,
//                    "min":293.52,
//                    "max":297.77,
//                    "night":293.52,
//                    "eve":297.77,
//                    "morn":297.77},
//        "pressure":925.04,
//                "humidity":76
   @SerializedName("day")
    private double day;
   @SerializedName("min")
    private double min;
   @SerializedName("max")
    private double max;
    @SerializedName("night")
    private double night;
    @SerializedName("eve")
    private double eve;
    @SerializedName("morn")
    private double morn;

    public TempModel() {
    }

    public TempModel(double day, double min, double max, double night, double eve, double morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}
