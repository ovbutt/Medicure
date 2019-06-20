package com.example.ovais.medicure;


public class AppointmentAlert {

    private long id;
    private String title;
    private String docname;
    private String docphonenum;
    private String docaddress;
    private String time;
    private String date;
    private String notes;

    public AppointmentAlert() {
    }

    public AppointmentAlert(String title, String docname, String docphonenum, String docaddress, String time, String date, String notes) {
        this.title = title;
        this.docname = docname;
        this.docphonenum = docphonenum;
        this.docaddress = docaddress;
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

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocphonenum() {
        return docphonenum;
    }

    public void setDocphonenum(String docphonenum) {
        this.docphonenum = docphonenum;
    }

    public String getDocaddress() {
        return docaddress;
    }

    public void setDocaddress(String docaddress) {
        this.docaddress = docaddress;
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
