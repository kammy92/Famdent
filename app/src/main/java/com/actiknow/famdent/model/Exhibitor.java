package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class Exhibitor {
    int id;
    String exhibitor_logo, exhibitor_name,hall_number,stall_number;

    public Exhibitor (int id, String exhibitor_logo, String exhibitor_name, String hall_number, String stall_number) {
        this.id = id;
        this.exhibitor_logo = exhibitor_logo;
        this.exhibitor_name = exhibitor_name;
        this.hall_number = hall_number;
        this.stall_number = stall_number;
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

    public String getHall_number () {
        return hall_number;
    }

    public void setHall_number (String hall_number) {
        this.hall_number = hall_number;
    }

    public String getStall_number () {
        return stall_number;
    }

    public void setStall_number (String stall_number) {
        this.stall_number = stall_number;
    }
}
