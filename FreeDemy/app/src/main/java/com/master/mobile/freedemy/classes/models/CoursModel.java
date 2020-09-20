package com.master.mobile.freedemy.classes.models;

import java.sql.Timestamp;

public class CoursModel {
    private String id;
    private String titre;
    private String description;
    private String contenu;
    private String date;

    public CoursModel(String id, String titre, String description, String contenu, String date) {
        this.id = id;
        this.titre = titre;
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.date = date;
    }

    public CoursModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
