package com.harsh.sainih.wirecampdemo1.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.harsh.sainih.wirecampdemo1.R;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.harsh.sainih.wirecampdemo1.viewmodel.ListWeekWeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    PlaceAutocompleteFragment autocompleteFragment;
    ListWeekWeatherViewModel listWeekWeatherViewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    public static String format = "json";
    public static String units = "metric";
    public static int numDays = 7;
    public static String appId = "54b59f16457d7798e6e6c6328385bb8b";
    public static double lon;
    public static double lat;
//    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        listWeekWeatherViewModel = ViewModelProviders.of(this).get(ListWeekWeatherViewModel.class);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Select City");

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setFilter(typeFilter);

        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<DayWeatherModel>(), new ArrayList<WeatherModel>(), this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lon = place.getLatLng().longitude;
                lat = place.getLatLng().latitude;
                listWeekWeatherViewModel.storeWeekWeatherData(lon, lat, format, units, numDays, appId).observe(AddActivity.this,
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(@Nullable Integer cityId) {
                                if(cityId!=-1)
                                listWeekWeatherViewModel.loadWeekWeatherData(cityId).observe(AddActivity.this, new Observer<List<DayWeatherModel>>() {
                                    @Override
                                    public void onChanged(@Nullable List<DayWeatherModel> dayWeatherModel) {
                                        recyclerViewAdapter.addItems(dayWeatherModel);
                                    }
                                });
                                else
                                    Toast.makeText(AddActivity.this,"No internet Connection",Toast.LENGTH_LONG);

                            }
                        });
                listWeekWeatherViewModel.loadWeatherConditions().observe(AddActivity.this, new Observer<List<WeatherModel>>() {
                    @Override
                    public void onChanged(@Nullable List<WeatherModel> weatherModels) {
                        recyclerViewAdapter.addWeatherConditions(weatherModels);
                    }
                });


            }

            @Override
            public void onError(Status status) {
                Toast.makeText(AddActivity.this, "Encountered an error", Toast.LENGTH_LONG).show();

            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
