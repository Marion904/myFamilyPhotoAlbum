package com.example.accueil.myfamilyphotoalbum.model;

import java.util.Date;

public class Text extends Content {


    public Text() {
    }

    public Text(User owner, String caption, Date date) {
        super(owner, caption, date);
    }

    public Text(User owner, String caption, Date date, Content origin) {
        super(owner, caption, date, origin);
    }


}
