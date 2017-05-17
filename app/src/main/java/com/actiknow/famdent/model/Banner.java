package com.actiknow.famdent.model;

/**
 * Created by Admin on 16-05-2017.
 */

public class Banner {
    int id;
    String title, image, type, url;

    public Banner (int id, String title, String image, String url, String type) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.type = type;
        this.url = url;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}
