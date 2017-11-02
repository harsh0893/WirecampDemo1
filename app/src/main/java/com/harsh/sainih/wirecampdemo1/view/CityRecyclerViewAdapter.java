package com.harsh.sainih.wirecampdemo1.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsh.sainih.wirecampdemo1.R;
import com.harsh.sainih.wirecampdemo1.model.CityModel;
import com.harsh.sainih.wirecampdemo1.model.DayWeatherModel;


import java.util.List;

/**
 * RecyclerViewAdapter for displaying cities in the first screen
 */

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.CityRecyclerViewHolder> {

    private List<CityModel> cityList;
     View.OnClickListener clickListener;

    public CityRecyclerViewAdapter(List<CityModel> cityList, View.OnClickListener onClickListener){
        //View.OnLongClickListener longClickListener) {
        this.cityList = cityList;
         this.clickListener = onClickListener;
    }

    @Override
    public CityRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityRecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final CityRecyclerViewHolder holder, int position) {
        CityModel cityModel =  cityList.get(position);
        holder.itemTextView.setText(cityModel.getName().toString()
        );
        holder.itemView.setTag(cityModel);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void addItems(List<CityModel> cityList) {
        this.cityList = cityList;
        notifyDataSetChanged();

    }

    static class CityRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;


        CityRecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.tv_location);

        }
    }
}