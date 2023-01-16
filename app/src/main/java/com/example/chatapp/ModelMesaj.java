package com.example.chatapp;

public class ModelMesaj {
    private String message, message_aciklama, uid;

    public ModelMesaj(String message, String message_aciklama, String uid) {
        this.message = message;
        this.message_aciklama = message_aciklama;
        this.uid = uid;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage (String message) {
        this.message = message;
    }
    public String getMessageAciklama() {
        return message_aciklama;
    }
    public void setMessageAciklama (String message_aciklama) {
        this.message_aciklama = message_aciklama;
    }
    public String getUid() {
        return uid;
    }
    public void setUid (String uid) {
        this.uid = uid;
    }


}
