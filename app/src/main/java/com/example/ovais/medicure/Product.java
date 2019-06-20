package com.example.ovais.medicure;

import android.support.v7.widget.RecyclerView;

import org.alicebot.ab.Sraix;

/**
 * Created by Ovais Butt on 3/25/2018.
 */

public class Product{

//    private double rating;
//    private int did, fees;
    private String rating, did, fees,dname, dspecilization, daddress, dtimings, dcontact, dlandline, demail;

    public Product(String did, String dname, String rating, String dspecilization, String fees, String daddress, String dtimings, String dcontact, String dlandline, String demail) {
        this.rating = rating;
        this.did = did;
        this.fees = fees;
        this.dname = dname;
        this.dspecilization = dspecilization;
        this.daddress = daddress;
        this.dtimings = dtimings;
        this.dcontact = dcontact;
        this.dlandline = dlandline;
        this.demail = demail;
    }

    public String getRating() {
        return rating;
    }

    public String getDid() {
        return did;
    }

    public String getFees() {
        return fees;
    }

    public String getDname() {
        return dname;
    }

    public String getDspecilization() {
        return dspecilization;
    }

    public String getDaddress() {
        return daddress;
    }

    public String getDtimings() {
        return dtimings;
    }

    public String getDcontact() {
        return dcontact;
    }

    public String getDlandline() {
        return dlandline;
    }

    public String getDemail() {
        return demail;
    }
}
