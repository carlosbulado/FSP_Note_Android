package com.carlosbulado.fsp_note.domain;

import java.util.Date;
import java.util.UUID;

public class Category extends Entity
{
    private String text;
    private Date created;
    private Date updated;

    public Category(String text, Date created, Date updated) {
        this.text = text;
        this.created = created;
        this.updated = updated;
    }

    public Category(String _id, String text, Date created, Date updated) {
        super(_id);
        this.text = text;
        this.created = created;
        this.updated = updated;
    }

    public Category()
    {
        this.setId(UUID.randomUUID().toString());
        this.text = "";
        this.created = new Date();
        this.updated = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setCreated(String created) { this.created = created != null && !created.isEmpty() ? new Date(created) : new Date(); }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setUpdated(String updated) { this.updated = updated != null && !updated.isEmpty() ? new Date(updated) : new Date(); }
}
