package com.example.uas_newper.Model;

public class AccountModel {

    String id;
    String level;
    String password;
    String image;
    String name;
    String email;

    public AccountModel(String id, String email, String level, String name, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.level = level;
        this.name = name;
    }

    public AccountModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
