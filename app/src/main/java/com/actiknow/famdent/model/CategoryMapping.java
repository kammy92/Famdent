package com.actiknow.famdent.model;

/**
 * Created by Admin on 16-05-2017.
 */

public class CategoryMapping {
    int exhibitor_id, category_id;
    String exhibitor_name;
    
    public CategoryMapping (int exhibitor_id, int category_id, String exhibitor_name) {
        this.exhibitor_id = exhibitor_id;
        this.category_id = category_id;
        this.exhibitor_name = exhibitor_name;
    }
    
    public int getExhibitor_id () {
        return exhibitor_id;
    }
    
    public void setExhibitor_id (int exhibitor_id) {
        this.exhibitor_id = exhibitor_id;
    }
    
    public int getCategory_id () {
        return category_id;
    }
    
    public void setCategory_id (int category_id) {
        this.category_id = category_id;
    }
    
    public String getExhibitor_name () {
        return exhibitor_name;
    }
    
    public void setExhibitor_name (String exhibitor_name) {
        this.exhibitor_name = exhibitor_name;
    }
}
