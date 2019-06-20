package com.example.ovais.medicure;

/**
 * Created by Ovais Butt on 3/27/2018.
 */

//MyDoctor1

public class Doctors {

    private long id;
    private String name;
    private String specialization;
    private String phonenum;
    private String landline;
    private String email;
    private String address;

    public Doctors() {
    }

    public Doctors(String name, String specialization, String phonenum, String landline, String email, String address) {
        this.name = name;
        this.specialization = specialization;
        this.phonenum = phonenum;
        this.landline = landline;
        this.email = email;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
