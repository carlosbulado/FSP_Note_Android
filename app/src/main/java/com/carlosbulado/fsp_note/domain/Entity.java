package com.carlosbulado.fsp_note.domain;

public abstract class Entity
{
    private String id;

    public Entity() { }

    public Entity(String _id)
    {
        this.id = _id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String _id) {
        this.id = _id;
    }
}
