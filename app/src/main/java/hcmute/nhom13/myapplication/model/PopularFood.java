package hcmute.nhom13.myapplication.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class PopularFood implements Serializable {
    private int id;
    private int id_res;
    private String name;
    private double price;
    private String description;
    private byte[] imageUrl;

    public PopularFood(String name, double price, byte[] imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public PopularFood() {
    }

    public PopularFood(int id, int id_res, String name, double price,String description, byte[] imageUrl) {
        this.id = id;
        this.id_res = id_res;
        this.name = name;
        this.price = price;
        this.description=description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public int getId_res() {
        return id_res;
    }

    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
