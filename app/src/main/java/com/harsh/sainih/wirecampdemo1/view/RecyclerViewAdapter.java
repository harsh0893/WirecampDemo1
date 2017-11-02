package com.harsh.sainih.wirecampdemo1.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harsh.sainih.wirecampdemo1.R;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;
import com.harsh.sainih.wirecampdemo1.model.WeatherModel;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<DayWeatherModel> dayWeatherList;
    private List<WeatherModel> weatherConditions;
    private Context mContext;
    //private View.OnLongClickListener longClickListener;

    public RecyclerViewAdapter(List<DayWeatherModel> dayWeatherList,List<WeatherModel> weatherConditions,Context context){
                               //View.OnLongClickListener longClickListener) {
        this.dayWeatherList = dayWeatherList;
        this.mContext = context;
        this.weatherConditions = weatherConditions;
       // this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dayweather_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        DayWeatherModel dayWeatherModel =  dayWeatherList.get(position);
        long date = dayWeatherModel.getDt();
        String day = getReadableDateString(date,position);
        holder.tvDay.setText(day);
        holder.tvTempHigh.setText(formatTemp(dayWeatherModel.getTemp().getMax()));
        holder.tvTempLow.setText(formatTemp(dayWeatherModel.getTemp().getMin()));
        int weatherId = dayWeatherModel.getWeatherId();
        String icon = "";
        String weather = "";
        for(int i=0;i<weatherConditions.size();i++)
        if(weatherId==weatherConditions.get(i).getId()){
            icon = weatherConditions.get(i).getIcon();
            weather = weatherConditions.get(i).getDescription();

        }
        if(!icon.equals("")) {
            Picasso.with(mContext).load("http://openweathermap.org/img/w/" + icon + ".png")
                    .noFade()
                    .into(holder.ivIcon);
        }
        holder.tvWeather.setText(weather);
        holder.itemView.setTag(dayWeatherModel);

      //  holder.itemView.setOnLongClickListener(longClickListener);
    }
    private String formatTemp(double high) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedTemp = Math.round(high);
        String unit = "°C";
        String tempStr = roundedTemp + unit;
        return tempStr;
    }

    private String getReadableDateString(long date,int position) {
        Time dayTime = new Time();
        dayTime.setToNow();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        dayTime = new Time();
        long dateTime;
        dateTime = dayTime.setJulianDay(julianStartDay + position);
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE, MMM dd");
        //   shortenedDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        return shortenedDateFormat.format(dateTime);
    }

    @Override
    public int getItemCount() {
        return dayWeatherList.size();
    }

    public void addWeatherConditions(List<WeatherModel> weatherConditions){
        this.weatherConditions = weatherConditions;
    }

    public void addItems(List<DayWeatherModel> dayWeatherList) {
        this.dayWeatherList = dayWeatherList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private TextView tvTempHigh;
        private TextView tvTempLow;
        private TextView tvWeather;
        private ImageView ivIcon;


        RecyclerViewHolder(View view) {
            super(view);
            tvDay = view.findViewById(R.id.tv_day);
            tvTempHigh = view.findViewById(R.id.tv_hightemp);
            tvTempLow = view.findViewById(R.id.tv_templow);
            tvWeather = view.findViewById(R.id.tv_weather);
            ivIcon = view.findViewById(R.id.iv_icon);

        }
    }
}