package com.texuna.reportGenerator.model;

public class Column {
    private String title, width;

    public Column() {
        title = "Title";
        width = "0";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

}
