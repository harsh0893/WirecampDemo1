package com.harsh.sainih.wirecampdemo1.model;

import android.arch.lifecycle.MutableLiveData;

import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** web service to fetch data on the day-to-day weather of a city from API
 * Created by sainih on 11/1/2017.
 */

public interface WeatherApiService {

    @GET("forecast/daily")
    Call<ApiResponse> getWeekWeather(@Query("lon") double lon,
                                                @Query("lat") double lat,
                                                @Query("mode") String mode,
                                                @Query("units") String units,
                                                @Query("cnt") int cnt,
                                                @Query("APPID") String APPID);

}
