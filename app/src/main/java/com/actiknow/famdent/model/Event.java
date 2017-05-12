package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class Event {
    int id;
    String program_name,doctor_name,date,time;

    public Event (int id, String program_name, String doctor_name, String date, String time) {
        this.id=id;
        this.program_name=program_name;
        this.doctor_name=doctor_name;
        this.date=date;
        this.time=time;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
