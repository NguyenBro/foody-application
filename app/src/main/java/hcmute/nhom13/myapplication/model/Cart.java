package hcmute.nhom13.myapplication.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private String idUser;
    private int idFood;
    private int quantity;

    public Cart(String idUser, int idFood, int quantity) {
        this.idUser = idUser;
        this.idFood = idFood;
        this.quantity = quantity;
    }

    public Cart(){

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
