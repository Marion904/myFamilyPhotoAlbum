package com.example.accueil.myfamilyphotoalbum.model;

public class User {
    private String email;
    private String lastName;
    private String firstName;
    private String userName;
    private String pwd;
    private boolean isModerator;
    private ContentFactory contentFactory;

    public User(String email,String userName,String pwd){
        this.email=email;
        this.userName=userName;
        this.pwd=pwd;
        isModerator=false;
    }

    public User(String email,String userName,String fN,String pwd){
        this.email=email;
        this.userName=userName;
        this.firstName=fN;
        isModerator=false;
    }

    public User(String email,String userName,String fN,String lN,String pwd){
        this.email=email;
        this.userName=userName;
        this.firstName=fN;
        this.lastName=lN;
        isModerator=false;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }



}
