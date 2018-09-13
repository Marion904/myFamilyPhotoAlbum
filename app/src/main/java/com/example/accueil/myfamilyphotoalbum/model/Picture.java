package com.example.accueil.myfamilyphotoalbum.model;

import android.net.Uri;

import java.util.Date;



public class Picture extends Content {
    private String url;

    public Picture() {
    }

    public Picture(String id,String owner, String caption, Date date, String url) {
        super(id, owner, caption, date);
        this.url=url;
    }
/*

    public Picture(String id,String owner, String caption, Date date, Content origin,String url) {
        super(id,owner, caption, date, origin);
        this.url=url;
    }
*/

    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url=url;
    }
    @Override
    public String getOwner() {
        return super.getOwner();
    }

    @Override
    public String getCaption() {
        return super.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        super.setCaption(caption);
    }

    @Override
    public String toString() {
        return "Picture";
    }

    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    public Content getOrigin() {
        return super.getOrigin();
    }

    @Override
    public float getRate() {
        return super.getRate();
    }

    @Override
    public void setRate(float rate) {
        super.setRate(rate);
    }

    @Override
    public void increaseRate(float rate) {
        super.increaseRate(rate);
    }


}
