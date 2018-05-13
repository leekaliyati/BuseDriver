package com.example.int_systems.busedriver;

public class BookingResponse {

    String Ridername;
    String no_pass;
    String address;
    String destination;
    String img_url;


    public BookingResponse(String ridername, String no_pass, String address, String destination, String img_url) {
        Ridername = ridername;
        this.no_pass = no_pass;
        this.address = address;
        this.destination = destination;
        this.img_url = img_url;
    }

    public String getRidername() {
        return Ridername;
    }

    public void setRidername(String ridername) {
        Ridername = ridername;
    }

    public String getNo_pass() {
        return no_pass;
    }

    public void setNo_pass(String no_pass) {
        this.no_pass = no_pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }





    }






