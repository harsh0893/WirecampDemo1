package com.harsh.sainih.wirecampdemo1.model;

import android.arch.lifecycle.LiveData;

import com.harsh.sainih.wirecampdemo1.model.database.Dao.CityModelDao;

import java.util.List;

/** Repository interacting with DB to fetch city entries for the first screem
 * Created by sainih on 11/2/2017.
 */

public class CityRepository {
    private CityModelDao cityModelDao;

    public CityRepository(CityModelDao cityModelDao) {
        this.cityModelDao = cityModelDao;

    }

    public synchronized LiveData<List<CityModel>> getCities(){
        return cityModelDao.getAllCityItems();

    }
}
