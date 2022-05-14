package com.agency11.sogutapp.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Diger_Yerler {
    String exp;
    ArrayList<String> imageUrl;
    GeoPoint location_id;
    String name;
    String phone;
    ArrayList<String> times;
    String videoId;
    String id;
    String location;

    public Diger_Yerler(){

    }

    public Diger_Yerler(String location,String id,String exp, ArrayList<String> imageUrl, GeoPoint location_id, String name, String phone, ArrayList<String> times, String videoId) {
        this.exp = exp;
        this.imageUrl = imageUrl;
        this.location_id = location_id;
        this.name = name;
        this.phone = phone;
        this.times = times;
        this.videoId = videoId;
        this.id = id;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public GeoPoint getLocation_id() {
        return location_id;
    }

    public void setLocation_id(GeoPoint location_id) {
        this.location_id = location_id;
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

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
