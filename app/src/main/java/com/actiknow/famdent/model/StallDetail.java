package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class StallDetail {

    String stall_name, hall_number, stall_number;

    public StallDetail (String stall_name, String hall_number, String stall_number) {
        this.stall_name = stall_name;
        this.hall_number = hall_number;
        this.stall_number = stall_number;
    }

    public String getStall_name () {
        return stall_name;
    }

    public void setStall_name (String stall_name) {
        this.stall_name = stall_name;
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
