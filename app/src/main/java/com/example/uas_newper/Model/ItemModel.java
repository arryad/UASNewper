package com.example.uas_newper.Model;

import java.io.Serializable;

public class ItemModel implements Serializable {
    public String key, nama, email, pic, deskripsi, deadline;

    public ItemModel() {

    }

    public ItemModel(String key, String nama, String email, String pic, String deskripsi, String deadline) {
        this.key = key;
        this.nama = nama;
        this.email = email;
        this.pic = pic;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
