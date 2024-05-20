package com.touqa.app.model;

public class Report {
    private String nameStreet;
    private String time;
    private String height;
    private String idUser;
    private String status;

    public Report(String nameStreet, String time, String height, String idUser) {
        this.nameStreet = nameStreet;
        this.time = time;
        this.height = height;
        this.idUser = idUser;
        this.status = "normal";
    }
    public Report() {

    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

