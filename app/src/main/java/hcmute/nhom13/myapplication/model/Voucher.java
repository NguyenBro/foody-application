package hcmute.nhom13.myapplication.model;

public class Voucher {
    private int id;
    private String body;
    private String hsd;
    private int image;

    public Voucher(int id,String body, String hsd, int image) {
        this.id = id;
        this.body = body;
        this.hsd = hsd;
        this.image = image;
    }

    public Voucher(){

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHsd() {
        return hsd;
    }

    public void setHsd(String hsd) {
        this.hsd = hsd;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
