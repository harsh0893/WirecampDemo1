package com.harsh.sainih.wirecampdemo1.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.harsh.sainih.wirecampdemo1.R;
import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.viewmodel.ListCitiesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    CityRecyclerViewAdapter cityRecyclerViewAdapter;
    ListCitiesViewModel listCitiesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView1);
        cityRecyclerViewAdapter = new CityRecyclerViewAdapter(new ArrayList<CityModel>(),this);

        listCitiesViewModel = ViewModelProviders.of(this).get(ListCitiesViewModel.class);
        listCitiesViewModel.loadCities().observe(MainActivity.this, new Observer<List<CityModel>>() {
            @Override
            public void onChanged(@Nullable List<CityModel> cityModels) {
                cityRecyclerViewAdapter.addItems(cityModels);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(cityRecyclerViewAdapter);




    }

    public void addNewWeatherLocation(View view) {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }

    @Override
    public void onClick(View view) {
        CityModel cityModel = (CityModel) view.getTag();
        Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
        detailIntent.putExtra("cityId",cityModel.getId());
        detailIntent.putExtra("modificationTime",cityModel.getModificationTime());
        detailIntent.putExtra("lon",cityModel.getCoord().getLon());
        detailIntent.putExtra("lat",cityModel.getCoord().getLat());
        startActivity(detailIntent);
    }
}
