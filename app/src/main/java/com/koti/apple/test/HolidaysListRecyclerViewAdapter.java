package com.koti.apple.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class HolidaysListRecyclerViewAdapter extends RecyclerView.Adapter<HolidaysListRecyclerViewAdapter.MyViewHolder> {

    private List<HolidaysPojo> holidaysPojosList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mHolidayTitle, mHolidayFromDate, mHolidayToDate, mHolidaySlash;

        private MyViewHolder(View view) {
            super(view);
            mHolidayTitle = view.findViewById(R.id.holiday_title);
            mHolidayFromDate = view.findViewById(R.id.holiday_from_date);
            mHolidayToDate = view.findViewById(R.id.holiday_to_date);
            mHolidaySlash = view.findViewById(R.id.holiday_slash);
        }
    }

    public HolidaysListRecyclerViewAdapter(List<HolidaysPojo> holidaysPojosList) {
        this.holidaysPojosList = holidaysPojosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holidays_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HolidaysPojo holidaysPojo = holidaysPojosList.get(position);
        holder.mHolidayTitle.setText(holidaysPojo.getTitle());
        holder.mHolidayFromDate.setText(holidaysPojo.getFromDate());
        if (holidaysPojo.getToDate() == null) {
            holder.mHolidaySlash.setVisibility(View.INVISIBLE);
            holder.mHolidayToDate.setVisibility(View.INVISIBLE);
        } else {
            holder.mHolidayToDate.setText(holidaysPojo.getToDate());
        }
    }

    @Override
    public int getItemCount() {
        return holidaysPojosList.size();
    }
}
