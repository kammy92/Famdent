package com.actiknow.famdent.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 27/04/2017.
 */

public class ExhibitorDetail {
    int id;
    boolean favourite;
    ArrayList<String> contactList = new ArrayList<> ();
    ArrayList<StallDetail> stallDetailList = new ArrayList<> ();
    String exhibitor_logo, exhibitor_name, address, email, website, notes;

    public ExhibitorDetail (int id, boolean favourite, String exhibitor_logo, String exhibitor_name, String address, String email, String website, String notes) {
        this.id = id;
        this.favourite = favourite;
        this.exhibitor_logo = exhibitor_logo;
        this.exhibitor_name = exhibitor_name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.notes = notes;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public boolean isFavourite () {
        return favourite;
    }

    public void setFavourite (boolean favourite) {
        this.favourite = favourite;
    }

    public ArrayList<String> getContactList () {
        return contactList;
    }

    public void setContactList (ArrayList<String> contactList) {
        this.contactList = contactList;
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

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getWebsite () {
        return website;
    }

    public void setWebsite (String website) {
        this.website = website;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public void setContactInList (String contact) {
        this.contactList.add (contact);
    }

    public List<StallDetail> getStallDetailList () {
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
}
