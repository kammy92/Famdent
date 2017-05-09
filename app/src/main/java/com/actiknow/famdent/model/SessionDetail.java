package com.actiknow.famdent.model;

import java.util.ArrayList;

/**
 * Created by l on 27/04/2017.
 */

public class SessionDetail {
    int id;
    boolean favourite;
    String title, date, time, location, category;
    ArrayList<SessionSpeaker> sessionSpeakerList = new ArrayList<> ();
    ArrayList<String> topicList = new ArrayList<> ();


    public SessionDetail (int id, boolean favourite, String title, String date, String time, String location, String category) {
        this.id = id;
        this.favourite = favourite;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.category = category;
    }


    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
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

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }


    public String getLocation () {
        return location;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public String getCategory () {
        return category;
    }

    public void setCategory (String category) {
        this.category = category;
    }


    public ArrayList<String> getTopicList () {
        return topicList;
    }

    public void setTopicList (ArrayList<String> topicList) {
        this.topicList = topicList;
    }

    public void setTopicInList (String topic) {
        this.topicList.add (topic);
    }

    public ArrayList<SessionSpeaker> getSessionSpeakerList () {
        return sessionSpeakerList;
    }

    public void setSessionSpeakerList (ArrayList<SessionSpeaker> sessionSpeakerList) {
        this.sessionSpeakerList = sessionSpeakerList;
    }
}
