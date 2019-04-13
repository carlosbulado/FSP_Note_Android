package com.carlosbulado.fsp_note.domain;

import java.util.Date;
import java.util.UUID;

public class Note extends Entity
{
    private String title;
    private String text;
    private double latitude;
    private double longitude;
    private String created;
    private String updated;
    private String category;


    public Note(String title, String text, double latitude, double longitude, String created, String updated) {
        this.title = title;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created = created;
        this.updated = updated;
    }

    public Note(String _id, String title, String text, double latitude, double longitude, String created, String updated) {
        super(_id);
        this.title = title;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created = created;
        this.updated = updated;
    }

    public Note()
    {
        this.setId(UUID.randomUUID().toString());
        this.title = "";
        this.text = "";
        this.latitude = 43.6452022996002;
        this.longitude = -79.38064366579056;
        this.created = "";
        this.updated = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    //public void setCreated(String created) { this.created = created != null &&  !created.isEmpty() ? new Date(created) : new Date(); }

    public String getUpdated() { return updated; }

    public void setUpdated(String updated) { this.updated = updated; }

    //public void setUpdated(String updated) { this.updated = updated != null && !updated.isEmpty() ? new Date(updated) : new Date(); }

    public String getCategoryName() { return "No category"; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
