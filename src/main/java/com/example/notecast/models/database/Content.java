package com.example.notecast.models.database;

public class Content {
    final int id;
    static String baseHTML;
    final String baseStyles;
    final String baseJS;
    static String rootLocation;

    public Content(int id, String baseHTML, String baseStyles, String baseJS, String rootLocation) {
        this.id = id;
        Content.baseHTML = baseHTML;
        this.baseStyles = baseStyles;
        this.baseJS = baseJS;
        Content.rootLocation = rootLocation;
    }

    public int getId() {
        return id;
    }

    public static String getBaseHTML() {
        return baseHTML;
    }

    public String getBaseStyles() {
        return baseStyles;
    }

    public String getBaseJS() {
        return baseJS;
    }

    public static String getRootLocation() {
        System.out.println(rootLocation);
        return rootLocation;
    }
}
