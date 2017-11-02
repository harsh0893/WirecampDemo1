package com.harsh.sainih.wirecampdemo1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.model.CityRepository;
import com.harsh.sainih.wirecampdemo1.model.database.AppDatabase;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.CityModelDao;

import java.util.List;

/**
 * Created by sainih on 11/1/2017.
 */

public class ListCitiesViewModel extends AndroidViewModel{

    private CityRepository cityRepository;
    private MediatorLiveData<List<CityModel>> mCities;
    AppDatabase appDatabase;
    private CityModelDao cityModelDao;

    public ListCitiesViewModel(Application application) {
        super(application);
        mCities = new MediatorLiveData<>();
        appDatabase = AppDatabase.getDatabase(application);
        cityModelDao = appDatabase.CityModelDao();
        cityRepository = new CityRepository(cityModelDao);

    }

    /**
     *
     * @return list of cities
     */
    public LiveData<List<CityModel>> loadCities() {
//
        mCities.addSource(
                cityRepository.getCities()
                ,new Observer<List<CityModel>>() {
                    @Override
                    public void onChanged(@Nullable List<CityModel> cityList) {
                        mCities.setValue(cityList);
                    }
                }
        );
        return mCities;


    }




}
