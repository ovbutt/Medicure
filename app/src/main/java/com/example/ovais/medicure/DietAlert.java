package com.example.ovais.medicure;


public class DietAlert {

    private long id;
    private String title;
    private String time;
    private String date;
    private String notes;

    public DietAlert() {
    }

    public DietAlert(String title, String time, String date, String notes) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
