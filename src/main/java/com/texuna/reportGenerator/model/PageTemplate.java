package com.texuna.reportGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class PageTemplate {
    private String width, height;
    List<Column> columns;

    public PageTemplate() {
        width = "0";
        height = "0";
        columns = new ArrayList<>();
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}
