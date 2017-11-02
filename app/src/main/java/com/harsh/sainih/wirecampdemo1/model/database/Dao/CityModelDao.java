package com.harsh.sainih.wirecampdemo1.model.database.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.model.CoordModel;
import com.harsh.sainih.wirecampdemo1.model.database.converters.CoordConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/** Dao for the
 *  City entries in the Database
 */

@Dao
@TypeConverters(CoordConverter.class)
public interface CityModelDao {

    @Query("select * from CityModel")
    LiveData<List<CityModel>> getAllCityItems();

    @Insert(onConflict = REPLACE)
    void addCities(List<CityModel> cityModelList);

    @Query("select * from CityModel where coord is :coord")
    int getCityId(String coord);



}
