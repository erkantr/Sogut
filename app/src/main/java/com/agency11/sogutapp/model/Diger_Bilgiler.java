package com.agency11.sogutapp.model;

public class Diger_Bilgiler {
    String exp;
    String image;
    String name;
    String id;

    public Diger_Bilgiler(){

    }

    public Diger_Bilgiler(String id,String exp, String image, String name) {
        this.exp = exp;
        this.image = image;
        this.name = name;
        this.id = id;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
