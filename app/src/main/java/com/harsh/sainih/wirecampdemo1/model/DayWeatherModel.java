package com.harsh.sainih.wirecampdemo1.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harsh.sainih.wirecampdemo1.model.database.converters.CoordConverter;
import com.harsh.sainih.wirecampdemo1.model.database.converters.TempConverter;

import java.util.ArrayList;

/** Model for storing and fetching day-to-day data with cityID foreign key
 * Created by sainih on 11/1/2017.
 */
@Entity(primaryKeys = {"dt", "cityId"},
        foreignKeys = @ForeignKey(entity = CityModel.class,
        parentColumns = "id",
        childColumns = "cityId"),
        indices = {@Index("cityId")}
)
public class DayWeatherModel {

    @SerializedName("dt")
    private long dt;

    @TypeConverters(TempConverter.class)
    private TempModel temp;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;
    @Ignore
    @SerializedName("weather")
    private ArrayList<WeatherModel> weather;
    @Expose(serialize = false,deserialize = false)
    private int cityId;
    @Expose(serialize = false,deserialize = false)
    private int weatherId;




    public DayWeatherModel(long dt, TempModel temp, double pressure, double humidity, int cityId, int weatherId) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cityId = cityId;
        this.weatherId = weatherId;


    }

    public DayWeatherModel(long dt, TempModel temp, double pressure, double humidity, ArrayList<WeatherModel> weather) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.cityId = 0;
        this.weatherId = 0;
    }

    public DayWeatherModel(long dt, TempModel temp, double pressure, double humidity, ArrayList<WeatherModel> weather, int cityId,int weatherId,CoordModel coord) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.cityId = cityId;
        this.weatherId = weatherId;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public TempModel getTemp() {
        return temp;
    }

    public void setTemp(TempModel temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public ArrayList<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherModel> weather) {
        this.weather = weather;
    }
}
