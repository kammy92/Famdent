package com.actiknow.famdent.model;

/**
 * Created by Admin on 16-05-2017.
 */

public class Category {
    int id;
    String name, level2, level3;
    
    public Category (int id, String name, String level2, String level3) {
        this.id = id;
        this.name = name;
        this.level2 = level2;
        this.level3 = level3;
    }
    
    public int getId () {
        return id;
    }
    
    public void setId (int id) {
        this.id = id;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getLevel2 () {
        return level2;
    }
    
    public void setLevel2 (String level2) {
        this.level2 = level2;
    }
    
    public String getLevel3 () {
        return level3;
    }
    
    public void setLevel3 (String level3) {
        this.level3 = level3;
    }
}
