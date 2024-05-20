package com.touqa.app.model;

public class Problem {
    private String name;
    private String desc;
    private String idUser;

    public Problem(String name, String desc, String idUser) {
        this.name = name;
        this.desc = desc;
        this.idUser = idUser;
    }

    // Empty constructor required for Firebase
    public Problem() {
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
