package com.harsh.sainih.wirecampdemo1.model.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.harsh.sainih.wirecampdemo1.model.TempModel;

import java.util.Locale;

/**
 * Created by sainih on 11/1/2017.
 */

public class TempConverter {
    @TypeConverter
    public static String fromTempModel(TempModel temp) {
        if (temp==null) {
            return(null);
        }

        return(String.format(Locale.US, "%f,%f,%f,%f,%f,%f", temp.getDay(),
                temp.getMin(),temp.getMax(),temp.getNight(),temp.getEve(),temp.getMorn()));
    }

    @TypeConverter
    public static TempModel toTempModel(String Alltemp) {
        if (Alltemp==null) {
            return(null);
        }

        String[] pieces=Alltemp.split(",");
        TempModel result=new TempModel();

        result.setDay(Double.parseDouble(pieces[0]));
        result.setMin(Double.parseDouble(pieces[1]));
        result.setMax(Double.parseDouble(pieces[2]));
        result.setNight(Double.parseDouble(pieces[3]));
        result.setEve(Double.parseDouble(pieces[4]));
        result.setMorn(Double.parseDouble(pieces[5]));


        return(result);
    }
}
