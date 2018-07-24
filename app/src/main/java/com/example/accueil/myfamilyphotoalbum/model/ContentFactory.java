package com.example.accueil.myfamilyphotoalbum.model;

 abstract class ContentFactory {
    private static final PictureFactory PIC_FACTORY = new PictureFactory();
    private static final TextFactory TEXT_FACTORY = new TextFactory();

    static ContentFactory getFactory(ContentType type){
        ContentFactory contentFactory=null;
        switch(type) {
            case TEXT:
                contentFactory=TEXT_FACTORY;
                break;
            case PICTURE:
                contentFactory=PIC_FACTORY;
                break;
        }
        return contentFactory;
    }


}
