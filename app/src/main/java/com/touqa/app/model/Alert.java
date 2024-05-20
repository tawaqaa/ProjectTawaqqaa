package com.touqa.app.model;

public class Alert {

    private String idUser;
    private String lat;
    private String lng;
    private String nameOfStreet;

    public Alert(String idUser, String lat, String lng, String nameOfStreet) {
        this.idUser = idUser;
        this.lat = lat;
        this.lng = lng;
        this.nameOfStreet = nameOfStreet;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNameOfStreet() {
        return nameOfStreet;
    }

    public void setNameOfStreet(String nameOfStreet) {
        this.nameOfStreet = nameOfStreet;
    }
}
