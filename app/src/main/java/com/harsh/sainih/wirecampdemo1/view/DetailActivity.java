package com.harsh.sainih.wirecampdemo1.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.harsh.sainih.wirecampdemo1.R;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.harsh.sainih.wirecampdemo1.viewmodel.ListWeekWeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ListWeekWeatherViewModel listWeekWeatherViewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    public static String format = "json";
    public static String units = "metric";
    public static int numDays = 7;
    public static String appId = "54b59f16457d7798e6e6c6328385bb8b";
    public static double lon;
    public static double lat;
    static int cityId;
    long modificationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<DayWeatherModel>(),new ArrayList<WeatherModel>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        if(getIntent().hasExtra("cityId")){
            cityId = getIntent().getIntExtra("cityId",0);
        }
        if(cityId==0)
            Toast.makeText(this,"Error fetching data",Toast.LENGTH_LONG).show();

        if(getIntent().hasExtra("modificationTime"))
            modificationTime = getIntent().getLongExtra("modificationTime",0);
        long timeSinceLastFetch;
        if(modificationTime == 0){
            timeSinceLastFetch = DateUtils.DAY_IN_MILLIS;
        }
        else
        timeSinceLastFetch = System.currentTimeMillis() - modificationTime;
        listWeekWeatherViewModel = ViewModelProviders.of(this).get(ListWeekWeatherViewModel.class);

        if(timeSinceLastFetch >= 3*(DateUtils.HOUR_IN_MILLIS)){
            lon = getIntent().getDoubleExtra("lon",0);
            lat = getIntent().getDoubleExtra("lat",0);
            if(lon!=0 && lat!=0)
            listWeekWeatherViewModel.storeWeekWeatherData(lon,lat,format,units,numDays,appId);
        }

        listWeekWeatherViewModel.loadWeekWeatherData(cityId).observe(DetailActivity.this, new Observer<List<DayWeatherModel>>() {
            @Override
            public void onChanged(@Nullable List<DayWeatherModel> dayWeatherModelList) {
                recyclerViewAdapter.addItems(dayWeatherModelList);

            }
        });

        listWeekWeatherViewModel.loadWeatherConditions().observe(DetailActivity.this, new Observer<List<WeatherModel>>() {
            @Override
            public void onChanged(@Nullable List<WeatherModel> weatherModels) {
                recyclerViewAdapter.addWeatherConditions(weatherModels);
            }
        });
    }
}
