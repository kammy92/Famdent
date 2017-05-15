package com.actiknow.famdent.model;


import java.util.ArrayList;

public class Exhibitor {
    int id;
    String exhibitor_logo, exhibitor_name, exhibitor_description;
    ArrayList<StallDetail> stallDetailList = new ArrayList<> ();

    public Exhibitor (int id, String exhibitor_logo, String exhibitor_name, String exhibitor_description) {
        this.id = id;
        this.exhibitor_logo = exhibitor_logo;
        this.exhibitor_name = exhibitor_name;
        this.exhibitor_description = exhibitor_description;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getExhibitor_logo () {
        return exhibitor_logo;
    }

    public void setExhibitor_logo (String exhibitor_logo) {
        this.exhibitor_logo = exhibitor_logo;
    }

    public String getExhibitor_name () {
        return exhibitor_name;
    }

    public void setExhibitor_name (String exhibitor_name) {
        this.exhibitor_name = exhibitor_name;
    }

    public ArrayList<StallDetail> getStallDetailList () {
        return stallDetailList;
    }

    public void setStallDetailList (ArrayList<StallDetail> stallDetailList) {
        this.stallDetailList = stallDetailList;
    }

    public void setStallDetailInList (StallDetail stallDetail) {
        this.stallDetailList.add (stallDetail);
    }

    public void clearStallDetailList () {
        this.stallDetailList.clear ();
    }

    public String getExhibitor_description () {
        return exhibitor_description;
    }

    public void setExhibitor_description (String exhibitor_description) {
        this.exhibitor_description = exhibitor_description;
    }
}