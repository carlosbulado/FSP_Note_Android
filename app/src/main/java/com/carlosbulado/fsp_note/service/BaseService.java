package com.carlosbulado.fsp_note.service;

import android.content.Context;

import com.carlosbulado.fsp_note.domain.Entity;
import com.carlosbulado.fsp_note.domain.Note;
import com.carlosbulado.fsp_note.repository.BaseRepository;

public abstract class BaseService<T extends Entity>
{
    private Context context;
    private BaseRepository<T> repository;

    public BaseService (Context context, BaseRepository repo)
    {
        this.context = context;
        this.repository = repo;
    }

    public Context getContext ()
    {
        return context;
    }

    public BaseRepository<T> getRepository ()
    {
        return repository;
    }

    public void setRepository (BaseRepository<T> repository)
    {
        this.repository = repository;
    }

    public T save(T obj)
    {
        T reg = this.getRepository().getById(obj.getId());
        if (reg != null)
            return this.getRepository().update(obj);
        else
            return this.getRepository().insert(obj);
    }

    public void delete(T obj)
    {
        this.getRepository().delete(obj);
    }
}
