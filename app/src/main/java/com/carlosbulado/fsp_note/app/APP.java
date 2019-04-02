package com.carlosbulado.fsp_note.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.carlosbulado.fsp_note.repository.NoteRepository;
import com.carlosbulado.fsp_note.service.CategoryService;
import com.carlosbulado.fsp_note.service.NoteService;

import java.util.HashMap;

public abstract class APP
{
    public static final String APP_FOLDER = "FSP_NOTES";
    public static final String APP_IMAGES_FOLDER = "IMAGES";
    public static final String APP_DATABASE_FOLDER = "DATABASE";
    public static final String APP_NOTES_INFO_FOLDER = "NOTES";
    public static final String APP_DATABASE_FILENAME = "FSP_NOTES_DATABASE.db";

    public static class NOTE
    {
        public static final String TABLENAME = "NOTE";
        public static final String ID = "NOTE_ID";
        public static final String TITLE = "NOTE_TITLE";
        public static final String TEXT = "NOTE_TEXT";
        public static final String LATITUDE = "NOTE_LATITUDE";
        public static final String LONGITUDE = "NOTE_LONGITUDE";
        public static final String CATEGORY = "NOTE_CATEGORY";
        public static final String CREATEDDATE = "NOTE_CREATED_DATE";
        public static final String UPDATEDATE = "NOTE_UPDATED_DATE";
        public static final HashMap<String, String> TABLE_COLUMNS = new HashMap<String, String>() {{
            put(ID, "TEXT");
            put(TITLE, "TEXT");
            put(TEXT, "TEXT");
            put(LATITUDE, "NUMERIC");
            put(LONGITUDE, "NUMERIC");
            put(CREATEDDATE, "TEXT");
            put(UPDATEDATE, "TEXT");
        }};
    }

    public static class CATEGORY
    {
        public static final String TABLENAME = "CATEGORY";
        public static final String ID = "CATEGORY_ID";
        public static final String TEXT = "CATEGORY_TEXT";
        public static final String IMAGE = "CATEGORY_IMAGE";
        public static final String CREATEDDATE = "CATEGORY_CREATED_DATE";
        public static final String UPDATEDATE = "CATEGORY_UPDATED_DATE";
        public static final HashMap<String, String> TABLE_COLUMNS = new HashMap<String, String>() {{
            put(ID, "TEXT");
            put(TEXT, "TEXT");
            put(CREATEDDATE, "TEXT");
            put(UPDATEDATE, "TEXT");
        }};
    }

    public static class Services
    {
        public static NoteService noteService;
        public static CategoryService categoryService;
    }

    public static void goTo(AppCompatActivity thisActivity, Class<?> nextActivity)
    {
        Intent mainIntent = new Intent(thisActivity, nextActivity);
        thisActivity.startActivity(mainIntent);
        thisActivity.finish();
    }
}
