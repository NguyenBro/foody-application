package hcmute.nhom13.myapplication.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Restaurant implements Serializable {
    int id;
    String name;
    String distance;
    byte[] imageUrl;
    String rating;
    String address;

    public Restaurant(String name, String price, byte[] imageUrl, String rating, String restorantname) {
        this.name = name;
        this.distance = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.address = restorantname;
    }

    public Restaurant(int id, String name, String price, byte[] imageUrl, String rating, String address) {
        this.id = id;
        this.name = name;
        this.distance = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.address = address;
    }

    public Restaurant(){

    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRestorantname() {
        return address;
    }

    public void setRestorantname(String restorantname) {
        this.address = restorantname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return distance;
    }

    public void setPrice(String price) {
        this.distance = price;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance= distance;
    }
}
