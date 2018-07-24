package com.example.accueil.myfamilyphotoalbum.model;

import java.util.Date;

public abstract class Content {

    private User owner;

    private String caption;

    private Date date;
    private Content origin;
    private float rate;

    public Content() {
    }

    public Content(User owner, String caption, Date date) {
        this.owner = owner;
        this.caption = caption;
        this.date = date;
        this.rate = 0;
    }
    public Content(User owner, String caption, Date date, Content origin) {
        this.owner = owner;
        this.caption = caption;
        this.date = date;
        this.origin = origin;
        this.rate = 0;
    }



    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setOrigin(Content origin) {
        this.origin = origin;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Content getOrigin() {
        return origin;
    }



    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void increaseRate(float rate) {
        this.rate += rate;
    }
}
