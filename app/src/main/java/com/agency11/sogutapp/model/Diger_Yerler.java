package com.agency11.sogutapp.model;

import java.util.ArrayList;

public class Diger_Yerler {
    String exp;
    ArrayList<String> imageUrl;
    ArrayList<String> location;
    String name;
    String phone;
    String time;
    String videoId;

    public Diger_Yerler(){

    }

    public Diger_Yerler(String exp, ArrayList<String> imageUrl, ArrayList<String> location, String name, String phone, String time, String videoId) {
        this.exp = exp;
        this.imageUrl = imageUrl;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.videoId = videoId;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
