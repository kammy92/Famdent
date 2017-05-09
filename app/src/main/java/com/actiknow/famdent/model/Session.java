package com.actiknow.famdent.model;

public class Session {
    int id;
    String program_name, doctor_name, date, time, location, category;

    public Session () {
    }

    public Session (int id, String program_name, String doctor_name, String date, String time, String location, String category) {
        this.id = id;
        this.program_name = program_name;
        this.doctor_name = doctor_name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.category = category;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getProgram_name () {
        return program_name;
    }

    public void setProgram_name (String program_name) {
        this.program_name = program_name;
    }

    public String getDoctor_name () {
        return doctor_name;
    }

    public void setDoctor_name (String doctor_name) {
        this.doctor_name = doctor_name;
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
}
