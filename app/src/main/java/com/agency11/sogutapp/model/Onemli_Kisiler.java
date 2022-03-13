package com.agency11.sogutapp.model;

import java.util.ArrayList;

public class Onemli_Kisiler {
    String birth;
    String children;
    String deathandtime;
    String exp;
    String grandchild;
    String graveLoc;
    ArrayList<String> imageUrl;
    String name;
    String sibs;

    public Onemli_Kisiler(){

    }

    public Onemli_Kisiler(String birth, String children, String deathandtime, String exp, String grandchild, String graveLoc, ArrayList<String> imageUrl, String name, String sibs) {
        this.birth = birth;
        this.children = children;
        this.deathandtime = deathandtime;
        this.exp = exp;
        this.grandchild = grandchild;
        this.graveLoc = graveLoc;
        this.imageUrl = imageUrl;
        this.name = name;
        this.sibs = sibs;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getDeathandtime() {
        return deathandtime;
    }

    public void setDeathandtime(String deathandtime) {
        this.deathandtime = deathandtime;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getGrandchild() {
        return grandchild;
    }

    public void setGrandchild(String grandchild) {
        this.grandchild = grandchild;
    }

    public String getGraveLoc() {
        return graveLoc;
    }

    public void setGraveLoc(String graveLoc) {
        this.graveLoc = graveLoc;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSibs() {
        return sibs;
    }

    public void setSibs(String sibs) {
        this.sibs = sibs;
    }
}
