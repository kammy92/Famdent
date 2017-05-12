package com.actiknow.famdent.model;

import java.util.ArrayList;

/**
 * Created by l on 27/04/2017.
 */

public class EventDetail {
    int id;
    boolean favourite;
    String name, date, time, duration, location, fees, notes;
    ArrayList<EventSpeaker> eventSpeakerList = new ArrayList<> ();
    ArrayList<String> topicList = new ArrayList<> ();

    public EventDetail (int id, boolean favourite, String name, String date, String time, String duration, String location, String fees, String notes) {
        this.id = id;
        this.favourite = favourite;
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.location = location;
        this.fees = fees;
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

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
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

    public String getDuration () {
        return duration;
    }

    public void setDuration (String duration) {
        this.duration = duration;
    }

    public String getLocation () {
        return location;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public String getFees () {
        return fees;
    }

    public void setFees (String fees) {
        this.fees = fees;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
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

    public ArrayList<EventSpeaker> getEventSpeakerList () {
        return eventSpeakerList;
    }

    public void setEventSpeakerList (ArrayList<EventSpeaker> eventSpeakerList) {
        this.eventSpeakerList = eventSpeakerList;
    }
}
