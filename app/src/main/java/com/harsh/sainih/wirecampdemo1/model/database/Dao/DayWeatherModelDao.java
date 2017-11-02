package com.harsh.sainih.wirecampdemo1.model.database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.harsh.sainih.wirecampdemo1.model.CoordModel;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.harsh.sainih.wirecampdemo1.model.database.converters.CoordConverter;
import com.harsh.sainih.wirecampdemo1.model.database.converters.TempConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/** the model to fetch the days weather forecast of a location
 * Created by sainih on 11/1/2017.
 */
@Dao
@TypeConverters({TempConverter.class, CoordConverter.class})
public interface DayWeatherModelDao {


    @Query("select * from DayWeatherModel where cityId = :cityId")
    LiveData<List<DayWeatherModel>> getAllDayWeatherbyCityId(int cityId);

    @Query("select * from DayWeatherModel")
    LiveData<List<DayWeatherModel>> getAllDayWeather();

    @Query("delete from DayWeatherModel where cityId = :cityId")
    void deleteCityWeather(int cityId);

    @Insert(onConflict = REPLACE)
    void addCityWeather(List<DayWeatherModel> dayWeatherModelList);
}
