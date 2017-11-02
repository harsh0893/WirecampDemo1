package com.harsh.sainih.wirecampdemo1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeekWeatherRepository;
import com.harsh.sainih.wirecampdemo1.model.database.AppDatabase;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.CityModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.DayWeatherModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.WeatherModelDao;

import java.util.List;
import java.util.concurrent.Executor;

/** ViewModel interacts with the Repository to fetch and store weather data
 * Created by sainih on 11/1/2017.
 */

public class ListWeekWeatherViewModel extends AndroidViewModel{

    private WeekWeatherRepository weekWeatherRepository;
    private MediatorLiveData<List<DayWeatherModel>> mDayWeather;
    private MediatorLiveData<List<WeatherModel>> mWeatherConditions;
    AppDatabase appDatabase;
    private WeatherModelDao weatherModelDao;
    private CityModelDao cityModelDao;
    private DayWeatherModelDao dayWeatherModelDao;
    private MediatorLiveData<Integer> cityIdLiveData;
    ApiDataSaveExecutor mExecutor = new ApiDataSaveExecutor();



        public ListWeekWeatherViewModel(Application application) {
            super(application);
            mDayWeather = new MediatorLiveData<>();
            cityIdLiveData = new MediatorLiveData<>();
            mWeatherConditions = new MediatorLiveData<>();
            appDatabase = AppDatabase.getDatabase(application);
            cityModelDao = appDatabase.CityModelDao();
            dayWeatherModelDao = appDatabase.DayWeatherModelDao();
            weatherModelDao = appDatabase.WeatherModelDao();
            weekWeatherRepository = new WeekWeatherRepository(cityModelDao,dayWeatherModelDao,weatherModelDao,mExecutor);

        }

    /**
     * load into the DB
     * @param lon
     * @param lat
     * @param mode
     * @param units
     * @param cnt
     * @param APPID
     * @return cityId
     */


    public LiveData<Integer> storeWeekWeatherData(@NonNull double lon, double lat, String mode, String units, int cnt, String APPID){
          cityIdLiveData.addSource(weekWeatherRepository.loadResponseIntoDB(lon, lat, mode, units, cnt, APPID),
                  new Observer<Integer>() {
              @Override
              public void onChanged(@Nullable Integer cityId) {
                  cityIdLiveData.setValue(cityId);
              }
          });
          return  cityIdLiveData;
    }

    /**
     * fetches weather conditions
     * @return all weather conditions
     */

    public LiveData<List<WeatherModel>> loadWeatherConditions(){
        mWeatherConditions.addSource(weekWeatherRepository.getWeatherConditions(), new Observer<List<WeatherModel>>() {
            @Override
            public void onChanged(@Nullable List<WeatherModel> weatherModels) {
                mWeatherConditions.setValue(weatherModels);
            }
        });
        return mWeatherConditions;
    }

    /**
     * fetches city's weather data to the View(activities)
     * @param cityId
     * @return
     */


   public LiveData<List<DayWeatherModel>> loadWeekWeatherData(Integer cityId) {
//
        mDayWeather.addSource(
                weekWeatherRepository.getWeekWeatherResponse(cityId)
                ,new Observer<List<DayWeatherModel>>() {
                    @Override
                    public void onChanged(@Nullable List<DayWeatherModel> dayWeatherModelList) {

                        mDayWeather.setValue(dayWeatherModelList);
                    }
                }
        );
        return mDayWeather;


    }

    public class ApiDataSaveExecutor implements Executor {

        @Override
        public synchronized void execute(@NonNull Runnable runnable) {
            new Thread(runnable).start();
        }
    }



}
