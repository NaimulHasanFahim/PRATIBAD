package com.example.myprojectwork;

public class Complaint{

    private  String message,reciever,sender,key;

    public String getMessage() {
        return message;
    }

    public Complaint() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Complaint(String message, String reciever, String sender,String key) {
        this.message = message;
        this.reciever = reciever;
        this.sender = sender;
        this.key = key;
    }
}
