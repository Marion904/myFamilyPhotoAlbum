package com.example.accueil.myfamilyphotoalbum.model;

import java.util.Date;

public class Text extends Content {


    public Text() {
    }

    public Text(String id,String owner, String caption, Date date) {
        super(id,owner, caption, date);
    }

    public Text(String id,String owner, String caption, Date date, Content origin) {
        super(id, owner, caption, date, origin);
    }

    @Override
    public String toString() {
        return "Text{}";
    }
}
