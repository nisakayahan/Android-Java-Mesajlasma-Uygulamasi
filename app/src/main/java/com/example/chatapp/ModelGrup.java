package com.example.chatapp;

import java.util.List;

public class ModelGrup {
    String grupadi, grupid, grupimage, grupaciklama;
    List<String> number;

    public ModelGrup() {
    }

    public ModelGrup(String grupadi, String grupid, String grupimage, String grupaciklama, List<String> number) {
        this.grupadi = grupadi;
        this.grupid = grupid;
        this.grupimage = grupimage;
        this.grupaciklama = grupaciklama;
        this.number = number;
    }

    public ModelGrup(String grupAdi, String aciklama, String grupSimge, List<String> number, String id) {
    }

    public String getGrupadi() {
        return grupadi;
    }

    public void setGrupadi(String grupadi) {
        this.grupadi = grupadi;
    }

    public String getGrupid() {
        return grupid;
    }

    public void setGrupid(String grupid) {
        this.grupid = grupid;
    }

    public String getGrupimage() {
        return grupimage;
    }

    public void setGrupimage(String grupimage) {
        this.grupimage = grupimage;
    }

    public String getGrupaciklama() {
        return grupaciklama;
    }

    public void setGrupaciklama(String grupaciklama) {
        this.grupaciklama = grupaciklama;
    }
    public List<String> getNumber() {
        return number;
    }
    public void setNumber(List<String> number) {
        this.number = number;
    }
}
