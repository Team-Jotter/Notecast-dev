package com.example.notecast.models.database;

import java.util.ArrayList;

public class User {

    final String name;
    final String email;
    final String password;
    final String profession;
    final QualityOfService qos;
    final ArrayList<Notebook> notebooks;


    public User(String name, String email, String password, String profession, QualityOfService qos, ArrayList<Notebook> notebooks) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profession = profession;
        this.qos = qos;
        if(notebooks == null)
            this.notebooks = new ArrayList<>();
        else
            this.notebooks = notebooks;
    }

    public void addNotebook(Notebook notebook) {
        this.notebooks.add(notebook);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfession() {
        return profession;
    }

    public QualityOfService getQos() {
        return qos;
    }

    public ArrayList<Notebook> getNotebooks() {
        return notebooks;
    }

    public Integer getSize() { return notebooks.size(); }
}
