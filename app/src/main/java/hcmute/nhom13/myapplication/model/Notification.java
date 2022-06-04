package hcmute.nhom13.myapplication.model;

public class Notification {
    private int id;
    private String id_user;
    private String type;
    private String date;
    private String time;
    private Double total;

    public Notification(int id, String id_user, String type, String date, String time, Double total) {
        this.id = id;
        this.id_user = id_user;
        this.type = type;
        this.date = date;
        this.time = time;
        this.total = total;
    }

    public Notification(){

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
}
