package com.example.foliyoo;

public class Bookings {
    private String from, to, booking_id, date;

    public Bookings() {
    }

    public Bookings(String from, String to, String booking_id,String date) {
        this.from = from;
        this.to = to;
        this.booking_id = booking_id;
        this.date=date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String name) {
        this.from = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datee) {
        this.date = datee;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String too) {
        this.to = too;
    }

    public String getBid() {
        return booking_id;
    }

    public void setBid(String booking) {
        this.booking_id = booking;
    }
}
