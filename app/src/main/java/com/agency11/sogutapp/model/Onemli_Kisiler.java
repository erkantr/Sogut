package com.agency11.sogutapp.model;

import java.util.ArrayList;

public class Onemli_Kisiler {
    String children;
    String exp;
    String name;
    String sibs;
    String wife;
    String mother;
    String father;
    String image;
    private String id;

    public Onemli_Kisiler(){

    }

    public Onemli_Kisiler(String id,String children, String exp, String name, String sibs, String wife, String mother, String father, String image) {
        this.children = children;
        this.exp = exp;
        this.name = name;
        this.sibs = sibs;
        this.wife = wife;
        this.mother = mother;
        this.father = father;
        this.image = image;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
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

    public String getWife() {
        return wife;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
