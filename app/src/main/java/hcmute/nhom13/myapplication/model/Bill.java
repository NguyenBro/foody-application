package hcmute.nhom13.myapplication.model;

public class Bill {
    private int id;
    private String id_user;
    private Double price;
    private Double fee;
    private Double voucher;
    private String note;
    private String status;
    private String date;
    private String time;
    private int image;

    public Bill(int id, String id_user, Double price, Double fee, Double voucher, String note, String date, String time,String status) {
        this.id = id;
        this.id_user = id_user;
        this.price = price;
        this.fee = fee;
        this.voucher = voucher;
        this.note = note;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public Bill(int id, String id_user, Double price, Double fee, Double voucher, String note, String status, String date, String time, int image) {
        this.id = id;
        this.id_user = id_user;
        this.price = price;
        this.fee = fee;
        this.voucher = voucher;
        this.note = note;
        this.status = status;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public Bill(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getVoucher() {
        return voucher;
    }

    public void setVoucher(Double voucher) {
        this.voucher = voucher;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
