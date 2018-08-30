package com.koti.apple.test;

public class HolidaysPojo {
    private String title;
    private String fromDate;
    private String toDate;
    private String fullDate;

    public HolidaysPojo(String title, String fromDate, String toDate, String fullDate) {
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fullDate = fullDate;
    }

    public HolidaysPojo(String title, String fromDate, String fullDate) {
        this.title = title;
        this.fromDate = fromDate;
        this.fullDate = fullDate;
    }

    public String getTitle() {
        return title;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFullDate() {
        return fullDate;
    }
}
