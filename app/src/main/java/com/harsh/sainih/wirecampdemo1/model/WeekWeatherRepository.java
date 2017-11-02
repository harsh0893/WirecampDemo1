package com.harsh.sainih.wirecampdemo1.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.harsh.sainih.wirecampdemo1.model.database.Dao.CityModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.DayWeatherModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.WeatherModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.converters.CoordConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Repository masking the database interaction and API interaction from the ViewModel and the View
 * Created by sainih on 11/1/2017.
 */

public class WeekWeatherRepository {
    private WeatherApiService webservice;
    private static WeekWeatherRepository weekWeatherRepository;
    private static Retrofit retrofit = null;
    private String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private WeatherModelDao weatherModelDao;
    private CityModelDao cityModelDao;
    private DayWeatherModelDao dayWeatherModelDao;
    private MutableLiveData<Integer> cityId = new MutableLiveData<>();
   Executor mExecutor;
    private final MutableLiveData<List<DayWeatherModel>> weekWeatherMutaleData = new MutableLiveData<>();

    public WeekWeatherRepository(CityModelDao cityModelDao,DayWeatherModelDao dayWeatherModelDao,
                                 WeatherModelDao weatherModelDao,
                                 Executor executor) {


        this.cityModelDao = cityModelDao;
        this.dayWeatherModelDao = dayWeatherModelDao;
        this.weatherModelDao = weatherModelDao;
        mExecutor = executor;
    }

    /**
     * to fetch week's weather data for a city
     * @param cityId
     * @return list of 7 days of weather data from a location identified by cityId
     */


    public synchronized LiveData<List<DayWeatherModel>> getWeekWeatherResponse(int cityId){
        return dayWeatherModelDao.getAllDayWeatherbyCityId(cityId);


    }

    /** gets the weather conditions
     *
     * @return returns are the possible weather conditions
     */

    public synchronized LiveData<List<WeatherModel>> getWeatherConditions(){
        return weatherModelDao.getAllWeatherItems();
    }

    /**
     * responsible for loading the data into DB
     * @param lon
     * @param lat
     * @param mode
     * @param units
     * @param cnt
     * @param APPID api key
     * @return id of the city
     */


    public synchronized MutableLiveData<Integer> loadResponseIntoDB(double lon, double lat,String mode, String units, int cnt, String APPID) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        webservice = retrofit.create(WeatherApiService.class);

        webservice.getWeekWeather(lon,lat,mode,units,cnt,APPID).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                cityId.setValue(response.body().getCity().getId());
                SaveData runnable = new SaveData(response.body());
                mExecutor.execute(runnable);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
               cityId.setValue(-1);

            }
        });

       return cityId;
    }





    class SaveData implements Runnable {
        ApiResponse apiResponse;

        SaveData(ApiResponse apiResponse) {
            this.apiResponse = apiResponse;
        }

        @Override
        public void run() {
            ArrayList<DayWeatherModel> dayWeatherModels = apiResponse.getList();
            int cityId = apiResponse.getCity().getId();
            String coordStr = CoordConverter.fromCoordModel(apiResponse.getCity().getCoord());
            for(int i =0;i<dayWeatherModels.size();i++){
                dayWeatherModels.get(i).setCityId(cityId);
                dayWeatherModels.get(i).setWeatherId(dayWeatherModels.get(i).getWeather().get(0).getId());

            }
            for(int i=0;i<apiResponse.getList().size();i++) {
                weatherModelDao.addWeather(dayWeatherModels.get(i).getWeather().get(0));

            }

            List<CityModel> cityModels = new ArrayList<CityModel>();
            CityModel cityModel = apiResponse.getCity();
            cityModel.setModificationTime(System.currentTimeMillis());
            cityModels.add(apiResponse.getCity());
            cityModelDao.addCities(cityModels);

           dayWeatherModelDao.deleteCityWeather(cityId);
            dayWeatherModelDao.addCityWeather(apiResponse.getList());

        }
    }
}
