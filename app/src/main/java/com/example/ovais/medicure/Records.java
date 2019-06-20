package com.example.ovais.medicure;

/**
 * Created by Ovais Butt on 3/23/2018.
 */


public class Records {
    private int id;

    private byte[] image;

    public Records(byte[] image, int id) {

        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
