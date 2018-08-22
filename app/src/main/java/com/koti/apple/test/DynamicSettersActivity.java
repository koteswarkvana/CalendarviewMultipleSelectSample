package com.koti.apple.test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class DynamicSettersActivity extends AppCompatActivity implements OnDateLongClickListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    MaterialCalendarView materialCalendarView;

    private int currentTileSize;
    private int currentTileWidth;
    private int currentTileHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialCalendarView = findViewById(R.id.calendarView);
        currentTileSize = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileWidth = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;
        currentTileHeight = MaterialCalendarView.DEFAULT_TILE_SIZE_DP;

        materialCalendarView.getSelectionMode();
//        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setOnDateLongClickListener(this);
        ///
//        materialCalendarView.

        Calendar instance = Calendar.getInstance();
//        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        // TODO: Uncomment below one when you want to set the current date.
//        materialCalendarView.setSelectedDate(instance);

        /*Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        materialCalendarView.state().edit()
                .setMinimumDate(instance1)
                .setMaximumDate(instance2)
                .commit();*/

        /*materialCalendarView.addDecorators(
                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );*/
        ArrayList<CalendarDay> dates = new ArrayList<>();
        ///

        //// Main dates select logic going on below code.
         // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendarInstance = Calendar.getInstance();
        for (int i = 0; i < 4; i++) {
            String date = i+"-08-2018";
            try {
                calendarInstance.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dates.add(CalendarDay.from(calendarInstance));
        }

        //// TODO: Add the alternative dates to the list: and check
        materialCalendarView.setSelectedDate(calendarInstance);
        materialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
       /*
        for (int i = 0; i < 3; i++) {
            CalendarDay day = CalendarDay.from(instance);
            dates.add(CalendarDay.from(calendarInstance));
        }
        materialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));*/

        /*for (int i = 0; i < 3; i++) {
            CalendarDay day = CalendarDay.from(instance);
//            dates.add(day);
            dates.add(CalendarDay.from(calendarInstance));
            instance.add(Calendar.DATE, 5);
        }*/
//        materialCalendarView.addDecorator(new EventDecorator(Color.RED, dates));
        ///
//        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    ///

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
    ///

    /*@OnClick(R.id.button_other_dates)
    void onOtherDatesClicked() {
        CharSequence[] items = {
                "Other Months",
                "Out Of Range",
                "Decorated Disabled",
                "Select days outside month"
        };
        final int[] itemValues = {
                MaterialCalendarView.SHOW_OTHER_MONTHS,
                MaterialCalendarView.SHOW_OUT_OF_RANGE,
                MaterialCalendarView.SHOW_DECORATED_DISABLED,
        };
        int showOtherDates = materialCalendarView.getShowOtherDates();

        boolean[] initSelections = {
                MaterialCalendarView.showOtherMonths(showOtherDates),
                MaterialCalendarView.showOutOfRange(showOtherDates),
                MaterialCalendarView.showDecoratedDisabled(showOtherDates),
                materialCalendarView.allowClickDaysOutsideCurrentMonth()
        };
        new AlertDialog.Builder(this)
                .setTitle("Show Other Dates")
                .setMultiChoiceItems(items, initSelections, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which < 3) {
                            int showOtherDates = materialCalendarView.getShowOtherDates();
                            if (isChecked) {
                                //Set flag
                                showOtherDates |= itemValues[which];
                            } else {
                                //Unset flag
                                showOtherDates &= ~itemValues[which];
                            }
                            materialCalendarView.setShowOtherDates(showOtherDates);
                        } else if (which == 3) {
                            materialCalendarView.setAllowClickDaysOutsideCurrentMonth(isChecked);
                        }
                    }
                })
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }*/

   /* @OnCheckedChanged(R.id.enable_save_current_position)
    void onSaveCurrentPositionChecked(boolean checked) {
        materialCalendarView.state().edit().isCacheCalendarPositionEnabled(checked).commit();
    }

    @OnCheckedChanged(R.id.show_week_days)
    void onShowWeekDaysChecked(boolean checked) {
        materialCalendarView.state().edit().setShowWeekDays(checked).commit();
    }

    @OnCheckedChanged(R.id.check_text_appearance)
    void onTextAppearanceChecked(boolean checked) {
        if (checked) {
            materialCalendarView.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
            materialCalendarView.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
            materialCalendarView.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        } else {
            materialCalendarView.setHeaderTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Header);
            materialCalendarView.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
            materialCalendarView.setWeekDayTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_WeekDay);
        }
        materialCalendarView.setShowOtherDates(checked ? MaterialCalendarView.SHOW_ALL : MaterialCalendarView.SHOW_NONE);
    }*/

    /*@OnCheckedChanged(R.id.check_page_enabled)
    void onPageEnabledChecked(boolean checked) {
        materialCalendarView.setPagingEnabled(checked);
    }

    @OnCheckedChanged(R.id.dynamic_height_enabled)
    void onDynamicHeightChecked(boolean checked) {
        materialCalendarView.setDynamicHeightEnabled(checked);
    }

    @OnClick(R.id.button_previous)
    void onPreviousClicked() {
        materialCalendarView.goToPrevious();
    }

    @OnClick(R.id.button_next)
    void onNextClicked() {
        materialCalendarView.goToNext();
    }

    @OnClick(R.id.button_min_date)
    void onMinClicked() {
        showDatePickerDialog(this, materialCalendarView.getMinimumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                materialCalendarView.state().edit()
                        .setMinimumDate(CalendarDay.from(year, monthOfYear, dayOfMonth))
                        .commit();
            }
        });
    }

    @OnClick(R.id.button_max_date)
    void onMaxClicked() {
        showDatePickerDialog(this, materialCalendarView.getMaximumDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                materialCalendarView.state().edit()
                        .setMaximumDate(CalendarDay.from(year, monthOfYear, dayOfMonth))
                        .commit();
            }
        });
    }

    @OnClick(R.id.button_selected_date)
    void onSelectedClicked() {
        showDatePickerDialog(this, materialCalendarView.getSelectedDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                materialCalendarView.setSelectedDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
            }
        });
    }*/

    /*@OnClick(R.id.button_toggle_topbar)
    void onToggleTopBarClicked() {
        materialCalendarView.setTopbarVisible(!materialCalendarView.getTopbarVisible());
    }

    Random random = new Random();

    @OnClick(R.id.button_set_colors)
    void onColorsClicked() {
        int color = Color.HSVToColor(new float[]{
                random.nextFloat() * 360,
                1f,
                0.75f
        });
        materialCalendarView.setArrowColor(color);
        materialCalendarView.setSelectionColor(color);
    }*/

    /*@OnClick(R.id.button_set_tile_size)
    void onTileSizeClicked() {
        final NumberPicker view = new NumberPicker(this);
        view.setMinValue(24);
        view.setMaxValue(64);
        view.setWrapSelectorWheel(false);
        view.setValue(currentTileSize);
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        currentTileSize = view.getValue();
                        materialCalendarView.setTileSizeDp(currentTileSize);
                    }
                })
                .show();
    }

    @OnClick(R.id.button_set_width_height)
    void onTileWidthHeightClicked() {
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        final NumberPicker pickerWidth = new NumberPicker(this);
        pickerWidth.setMinValue(24);
        pickerWidth.setMaxValue(64);
        pickerWidth.setWrapSelectorWheel(false);
        pickerWidth.setValue(currentTileWidth);
        final NumberPicker pickerHeight = new NumberPicker(this);
        pickerHeight.setMinValue(24);
        pickerHeight.setMaxValue(64);
        pickerHeight.setWrapSelectorWheel(false);
        pickerHeight.setValue(currentTileHeight);
        layout.addView(pickerWidth);
        layout.addView(pickerHeight);
        new AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        currentTileWidth = pickerWidth.getValue();
                        currentTileHeight = pickerHeight.getValue();
                        materialCalendarView.setTileSize(-1);
                        materialCalendarView.setTileWidthDp(currentTileWidth);
                        materialCalendarView.setTileHeightDp(currentTileHeight);
                    }
                })
                .show();
    }

    @OnClick(R.id.button_clear_selection)
    void onClearSelection() {
        materialCalendarView.clearSelection();
    }*/

    ///
    /*@OnClick(R.id.button_selection_mode)
    void onChangeSelectionMode() {
        CharSequence[] items = {
                "No Selection",
                "Single Date",
                "Multiple Dates",
                "Range of Dates"
        };
        new AlertDialog.Builder(this)
                .setTitle("Selection Mode")
                .setSingleChoiceItems(items, materialCalendarView.getSelectionMode(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        materialCalendarView.setSelectionMode(which);
                        dialog.dismiss();
                    }
                })
                .show();
    }*/
    ///

    /*@OnClick(R.id.button_change_orientation)
    void onButtonChangeOrientation() {
        materialCalendarView.setTitleAnimationOrientation(
                materialCalendarView.getTitleAnimationOrientation() == MaterialCalendarView.VERTICAL
                        ? MaterialCalendarView.HORIZONTAL
                        : MaterialCalendarView.VERTICAL);

        Toast.makeText(this,
                materialCalendarView.getTitleAnimationOrientation() == MaterialCalendarView.VERTICAL
                        ? "Vertical"
                        : "Horizontal",
                Toast.LENGTH_SHORT).show();
    }

    private static final int[] DAYS_OF_WEEK = {
            Calendar.SUNDAY,
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.SATURDAY,
    };

    @OnClick(R.id.button_set_first_day)
    void onFirstDayOfWeekClicked() {
        int index = random.nextInt(DAYS_OF_WEEK.length);
        materialCalendarView.state().edit().setFirstDayOfWeek(DAYS_OF_WEEK[index]).commit();

    }

    @OnClick(R.id.button_weeks)
    public void onSetWeekMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && animateModeTransition.isChecked()) {
            TransitionManager.beginDelayedTransition(parent);
        }
        materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
    }

    @OnClick(R.id.button_months)
    public void onSetMonthMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && animateModeTransition.isChecked()) {
            TransitionManager.beginDelayedTransition(parent);
        }
        materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
    }


    public static void showDatePickerDialog(Context context, CalendarDay day,
                                            DatePickerDialog.OnDateSetListener callback) {
        if (day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, 0, callback, day.getYear(), day.getMonth(), day.getDay()
        );
        dialog.show();
    }
    */

    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        Toast.makeText(this, FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }
}
