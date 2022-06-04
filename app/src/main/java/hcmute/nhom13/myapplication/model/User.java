package hcmute.nhom13.myapplication.model;

import java.io.Serializable;

public class User implements Serializable {
    private String phone;
    private String name;
    private String password;
    private String birth;
    private String email;

    public User(String phone, String name, String password, String birth, String email) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.birth = birth;
        this.email = email;
    }

    public User(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
