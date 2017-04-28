package com.actiknow.famdent.model;

/**
 * Created by l on 27/04/2017.
 */

public class HomeService {

    int id,icon;
    String image, service_name;

    public HomeService (int id, int icon, String image, String service_name) {
        this.id = id;
        this.icon = icon;
        this.image = image;
        this.service_name = service_name;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getIcon () {
        return icon;
    }

    public void setIcon (int icon) {
        this.icon = icon;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public String getService_name () {
        return service_name;
    }

    public void setService_name (String service_name) {
        this.service_name = service_name;
    }
}
