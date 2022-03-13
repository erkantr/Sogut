package com.agency11.sogutapp.model;

import android.widget.ImageView;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public class Tarihi_Yerler {
    private String exp;
    private ArrayList<String> imageUrl;
    private ArrayList<String> location;
    private String image;
    private String name;
    private String phone;
    private String id;
    private ArrayList<String> times;
    private String videoId;

    public Tarihi_Yerler(){

    }

    public Tarihi_Yerler(String id,String exp, ArrayList<String> imageUrl, ArrayList<String> location, String image, String name, String phone, ArrayList<String> times,String videoId) {
        this.exp = exp;
        this.id = id;
        this.imageUrl = imageUrl;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.times = times;
        this.image = image;
        this.videoId = videoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public List<String> getImageUrl() {
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
