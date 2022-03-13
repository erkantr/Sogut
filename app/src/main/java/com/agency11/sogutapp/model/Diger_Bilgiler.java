package com.agency11.sogutapp.model;

public class Diger_Bilgiler {
    String exp;
    String imageUrl;
    String name;

    public Diger_Bilgiler(){

    }

    public Diger_Bilgiler(String exp, String imageUrl, String name) {
        this.exp = exp;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
