package com.actiknow.famdent.model;


import java.util.ArrayList;
import java.util.List;

public class Exhibitor {
    int id;
    String exhibitor_logo, exhibitor_name;
    List<StallDetail> stallDetailList = new ArrayList<> ();

    public Exhibitor (int id, String exhibitor_logo, String exhibitor_name, List<StallDetail> stallDetailList) {
        this.id = id;
        this.exhibitor_logo = exhibitor_logo;
        this.exhibitor_name = exhibitor_name;
        this.stallDetailList = stallDetailList;

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

    public List<StallDetail> getStallDetailList () {
        return stallDetailList;
    }

    public void setStallDetailList (List<StallDetail> stallDetailList) {
        this.stallDetailList = stallDetailList;
    }

    public void setStallDetailInList (StallDetail stallDetail) {
        this.stallDetailList.add (stallDetail);
    }
}