package com.touqa.app.model;

public class PageItem {

    private int imageRes;
    private String text;

    public PageItem(int imageRes, String text) {
        this.imageRes = imageRes;
        this.text = text;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getText() {
        return text;
    }
}
