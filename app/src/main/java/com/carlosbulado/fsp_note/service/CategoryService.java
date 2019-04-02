package com.carlosbulado.fsp_note.service;

import android.content.Context;

import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;
import com.carlosbulado.fsp_note.repository.CategoryRepository;
import com.carlosbulado.fsp_note.repository.NoteRepository;

public class CategoryService extends BaseService<Category>
{
    public CategoryService(Context context)
    {
        super(context, new CategoryRepository(context));
    }
}
