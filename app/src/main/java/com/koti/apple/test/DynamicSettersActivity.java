package com.koti.apple.test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class DynamicSettersActivity extends AppCompatActivity implements OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    MaterialCalendarView materialCalendarView;
    private int currentTileSize;
    private int currentTileWidth;
    private int currentTileHeight;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<HolidaysPojo> holidaysPojosList = new ArrayList<>();
    String mTitle = "Annual day", mEventFromDate = "Events selected";
    String[] monthAndYear = {"17-8-2018", "19-8-2018", "22-8-2018", "25-8-2018", "17-9-2018", "18-9-2018", "22-9-2018" , "22-2-2018" , "22-1-2019" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialCalendarView = findViewById(R.id.calendarView);
        currentTileSize = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileWidth = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileHeight = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        materialCalendarView.getSelectionMode();
        ///
        mRecyclerView = findViewById(R.id.calender_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new HolidaysListRecyclerViewAdapter(holidaysPojosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // adding the row line
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        materialCalendarView.setOnMonthChangedListener(this);
        ///

        ArrayList<CalendarDay> dates = new ArrayList<>();
        //// Main dates select logic going on below code.
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendarInstance = Calendar.getInstance();
        String date = "-08-2018";
        for (int i = 0; i < monthAndYear.length; i++) {
            if (i < 4) {
                date = i + "-08-2018";
                holidaysPojosList.add(new HolidaysPojo(mTitle+i, mEventFromDate+i, monthAndYear[i]));
            } else {
                date = i + "-09-2018";
                holidaysPojosList.add(new HolidaysPojo(mTitle+i, mEventFromDate+i, monthAndYear[i]));
            }
            try {
//                calendarInstance.setTime(sdf.parse(date));
                calendarInstance.setTime(sdf.parse(monthAndYear[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dates.add(CalendarDay.from(calendarInstance));
        }
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        materialCalendarView.setSelectedDate(calendarInstance);
        materialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
        Log.e("info >> ", "onCreate: >> "+ materialCalendarView.getCurrentDate().toString().substring(12,19).split("-")[1]);
        String[] yearAndMonth = materialCalendarView.getCurrentDate().toString().substring(12,19).split("-");

        List<HolidaysPojo> currentHolidaysList = new ArrayList<>();
        for (int i = 0; i < holidaysPojosList.size(); i++) {
            String savedExistDate = holidaysPojosList.get(i).getFullDate();
            String[] savedExistDateArray = savedExistDate.split("-");
            if (yearAndMonth[0].equalsIgnoreCase(savedExistDateArray[2]) && savedExistDateArray[1].equalsIgnoreCase(String.valueOf(Integer.parseInt(materialCalendarView.getCurrentDate().toString().substring(12, 19).split("-")[1]) + 1))) {
                Log.e("info >> ", "onCreate: get full date >> " + holidaysPojosList.get(i).getFullDate());
                currentHolidaysList.add(new HolidaysPojo(holidaysPojosList.get(i).getTitle(), holidaysPojosList.get(i).getFromDate(), holidaysPojosList.get(i).getToDate(), holidaysPojosList.get(i).getFullDate()));
            }
        }
        if (currentHolidaysList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter = new HolidaysListRecyclerViewAdapter(currentHolidaysList);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        String[] yearAndMonth = date.toString().substring(12,19).split("-");

        List<HolidaysPojo> currentHolidaysList = new ArrayList<>();
        for (int i = 0; i < holidaysPojosList.size(); i++) {
            String savedExistDate = holidaysPojosList.get(i).getFullDate();
            String[] savedExistDateArray = savedExistDate.split("-");
            if (yearAndMonth[0].equalsIgnoreCase(savedExistDateArray[2]) && savedExistDateArray[1].equalsIgnoreCase(String.valueOf(Integer.parseInt(date.toString().substring(12,19).split("-")[1])+1))) {
                Log.e("info >> ", "onMonthChanged: get full date >> " + holidaysPojosList.get(i).getFullDate());
                currentHolidaysList.add(new HolidaysPojo(holidaysPojosList.get(i).getTitle(), holidaysPojosList.get(i).getFromDate(), holidaysPojosList.get(i).getToDate(), holidaysPojosList.get(i).getFullDate()));
            }
        }

        if (currentHolidaysList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter = new HolidaysListRecyclerViewAdapter(currentHolidaysList);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }


    private void filterMonth(String fullDate, String currentSwipedfullDate) {
        List<HolidaysPojo> currentHolidaysList = new ArrayList<>();
        String[] splitFullDate = fullDate.split("-");
        String[] splitCurrentSwipedFullDate = currentSwipedfullDate.split("-");
        for (int i = 0; i < holidaysPojosList.size(); i++) {
            String savedExistDate = holidaysPojosList.get(i).getFullDate();
            String[] savedExistDateArray = savedExistDate.split("-");
            if (splitFullDate[0].equalsIgnoreCase(savedExistDateArray[0]) && splitFullDate[1].equalsIgnoreCase(savedExistDateArray[1].substring(1,2))) {
                Log.e("info >> ", "onMonthChanged: get full date >> " + holidaysPojosList.get(i).getFullDate());
                currentHolidaysList.add(new HolidaysPojo(holidaysPojosList.get(i).getTitle(), holidaysPojosList.get(i).getFromDate(), holidaysPojosList.get(i).getToDate(), holidaysPojosList.get(i).getFullDate()));
            }
        }
        mAdapter = new HolidaysListRecyclerViewAdapter(currentHolidaysList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }
}
