package com.example.smartkissan;

// Booking.java
public class Booking {
    private String date;
    private String time;
    private String service;
    private String activity;
    private String bill;

    public Booking(String date, String time, String service, String activity, String bill) {
        this.date = date;
        this.time = time;
        this.service = service;
        this.activity = activity;
        this.bill = bill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}


