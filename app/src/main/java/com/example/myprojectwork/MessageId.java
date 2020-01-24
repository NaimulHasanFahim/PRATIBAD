package com.example.myprojectwork;

public class MessageId {

    private String message;
    private String sender;
    private String reciever;

    public MessageId(String message, String sender, String reciever) {
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
    }

    public MessageId() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }
}
