package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class Favourite {
    int id, session_id, event_id, exhibitor_id;
    String type;

    public Favourite (int id, int session_id, int event_id, int exhibitor_id, String type) {
        this.id = id;
        this.session_id = session_id;
        this.event_id = event_id;
        this.exhibitor_id = exhibitor_id;
        this.type = type;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getSession_id () {
        return session_id;
    }

    public void setSession_id (int session_id) {
        this.session_id = session_id;
    }

    public int getEvent_id () {
        return event_id;
    }

    public void setEvent_id (int event_id) {
        this.event_id = event_id;
    }

    public int getExhibitor_id () {
        return exhibitor_id;
    }

    public void setExhibitor_id (int exhibitor_id) {
        this.exhibitor_id = exhibitor_id;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }
}