package com.example.uas_newper.Model;

public class AccountModel {

    String id;
    String level;
    String image;
    String name;
    String email;

    public AccountModel(String id, String level, String email, String name, String image) {
        this.id = id;
        this.email = email;
        this.level = level;
        this.name = name;
        this.image = image;
    }

    public AccountModel() {

    }

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

}
