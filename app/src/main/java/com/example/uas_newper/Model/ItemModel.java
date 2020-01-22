package com.example.uas_newper.Model;

import java.io.Serializable;

public class ItemModel implements Serializable {
    public String key, judul, nama, email, pic, status, deskripsi, deadline, kategori, sk, publisher;

    public ItemModel() {

    }

    public ItemModel(String key, String judul, String nama, String email, String pic, String status, String deskripsi, String deadline, String kategori, String sk, String publisher) {
        this.key = key;
        this.nama = nama;
        this.email = email;
        this.pic = pic;
        this.status = status;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.judul = judul;
        this.kategori = kategori;
        this.sk = sk;
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
