package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class ProgrammeSpeaker {
    int id;
    String name, qualification, experience;

    public ProgrammeSpeaker (int id, String name, String qualification, String experience) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.experience = experience;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getQualification () {
        return qualification;
    }

    public void setQualification (String qualification) {
        this.qualification = qualification;
    }

    public String getExperience () {
        return experience;
    }

    public void setExperience (String experience) {
        this.experience = experience;
    }
}
