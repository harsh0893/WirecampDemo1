package com.harsh.sainih.wirecampdemo1.model.database.converters;
import android.arch.persistence.room.TypeConverter;

import com.harsh.sainih.wirecampdemo1.model.CoordModel;

import java.util.Locale;

/**
 * Converter class to convert CoordModel object into string to be stored in db and vice-versaf
 */

public class CoordConverter {

    @TypeConverter
    public static String fromCoordModel(CoordModel coords) {
        if (coords==null) {
            return(null);
        }

        return(String.format(Locale.US, "%f,%f", coords.getLat(),
                coords.getLon()));
    }

    @TypeConverter
    public static CoordModel toCoordModel(String latlon) {
        if (latlon==null) {
            return(null);
        }

        String[] pieces=latlon.split(",");
        CoordModel result=new CoordModel();

        result.setLat(Double.parseDouble(pieces[0]));
        result.setLon(Double.parseDouble(pieces[1]));

        return(result);
    }
}
