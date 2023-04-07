package com.example.notecast.models.database;

public class QualityOfService {
    final int id;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    final String type;
    // TODO: add more attributes later

    public QualityOfService(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
