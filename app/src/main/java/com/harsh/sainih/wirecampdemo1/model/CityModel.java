package com.harsh.sainih.wirecampdemo1.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.harsh.sainih.wirecampdemo1.model.database.converters.CoordConverter;

/** Model to fetch data from the APIs as well as storing into SQLite table
 * Created by sainih on 11/1/2017.
 */
@Entity
public class CityModel {
    @PrimaryKey
    private int id;

    private String name;

    @TypeConverters(CoordConverter.class)
    private CoordModel coord;

    private String country;

    @Expose(serialize = false, deserialize = false)
    private long modificationTime;

    public CityModel(int id, String name, CoordModel coord, String country) {
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.modificationTime = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordModel getCoord() {
        return coord;
    }

    public void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }



}

