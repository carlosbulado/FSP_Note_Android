package com.carlosbulado.fsp_note.service;

import android.content.Context;

import com.carlosbulado.fsp_note.domain.Note;
import com.carlosbulado.fsp_note.repository.NoteRepository;

public class NoteService extends BaseService<Note>
{
    public NoteService (Context context)
    {
        super(context, new NoteRepository(context));
    }
}
