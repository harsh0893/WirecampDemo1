package com.harsh.sainih.wirecampdemo1.model.database;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.CityModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.DayWeatherModelDao;
import com.harsh.sainih.wirecampdemo1.model.database.Dao.WeatherModelDao;

/** The instance of the database
 * Created by sainih on 11/1/2017.
 */


@Database(entities = {CityModel.class, DayWeatherModel.class, WeatherModel.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "weather_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract CityModelDao CityModelDao();

    public abstract DayWeatherModelDao DayWeatherModelDao();

    public abstract WeatherModelDao WeatherModelDao();


}
