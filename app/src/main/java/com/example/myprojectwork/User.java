package com.example.myprojectwork;


public class User {

    private String uid;
    private String username;
    private String imageURL;
    private String phone;
    private String address;

    public User(String uid, String username, String imageURL,String address, String phone) {
        this.uid = uid;
        this.username = username;
        this.imageURL = imageURL;
        this.phone = phone;
        this.address = address;
    }
    public  User( ){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
