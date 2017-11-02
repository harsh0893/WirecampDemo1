package com.harsh.sainih.wirecampdemo1.model.database.Dao;

/**
 * Created by sainih on 11/1/2017.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Dao for accessing weather conditions
 */

@Dao
public interface WeatherModelDao {

    @Query("select * from WeatherModel")
    LiveData<List<WeatherModel>> getAllWeatherItems();

    @Query("select * from WeatherModel where id = :id")
    WeatherModel getWeatherItembyId(int id);

    @Insert(onConflict = REPLACE)
    void addWeather(WeatherModel weatherModel);

    @Delete
    void deleteWeather(WeatherModel weatherModel);

    @Insert(onConflict = ABORT)
    void addWeathers(List<WeatherModel> weatherModelList);


}