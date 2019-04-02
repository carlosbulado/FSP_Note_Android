package com.carlosbulado.fsp_note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.carlosbulado.fsp_note.app.APP;

public class Database extends SQLiteOpenHelper
{
    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 1;

    public Database (Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase)
    {
        try
        {
            String noteTable = " CREATE TABLE " + APP.NOTE.TABLENAME
                    + " ( " + APP.NOTE.ID + " TEXT PRIMARY KEY,"
                    + APP.NOTE.TITLE + " TEXT, "
                    + APP.NOTE.TEXT + " TEXT, "
                    + APP.NOTE.LATITUDE + " NUMERIC,"
                    + APP.NOTE.LONGITUDE + " NUMERIC,"
                    + APP.NOTE.CATEGORY + " INTEGER,"
                    + APP.NOTE.CREATEDDATE + " TEXT,"
                    + APP.NOTE.UPDATEDATE + " TEXT) ";

            sqLiteDatabase.execSQL(noteTable);
        }
        catch (Exception ex) { }

        try
        {
            String categoryTable = " CREATE TABLE " + APP.CATEGORY.TABLENAME
                    + " ( " + APP.CATEGORY.ID + " TEXT PRIMARY KEY,"
                    + APP.CATEGORY.TEXT + " TEXT, "
                    + APP.CATEGORY.CREATEDDATE + " TEXT,"
                    + APP.CATEGORY.UPDATEDATE + " TEXT) ";

            sqLiteDatabase.execSQL(categoryTable);
        }
        catch (Exception ex) { }

        this.createSomeNotesForTest(sqLiteDatabase);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        try { sqLiteDatabase.execSQL(" DROP TABLE " + APP.NOTE.TABLENAME); }
        catch (Exception ex) { }
        try { sqLiteDatabase.execSQL(" DROP TABLE " + APP.CATEGORY.TABLENAME); }
        catch (Exception ex) { }
        onCreate(sqLiteDatabase);
    }

    private void createSomeNotesForTest(SQLiteDatabase sqLiteDatabase)
    {
        String insert_subject_student = "INSERT INTO " + APP.NOTE.TABLENAME
                + " ( " + APP.NOTE.ID + ", "
                + APP.NOTE.TITLE + ", " +
                APP.NOTE.TEXT + ") "
                + " VALUES ('1', 'First Note', 'Text 01')";

        sqLiteDatabase.execSQL(insert_subject_student);

        insert_subject_student = "INSERT INTO " + APP.NOTE.TABLENAME
                + " ( " + APP.NOTE.ID + ", "
                + APP.NOTE.TITLE + ", " +
                APP.NOTE.TEXT + ") "
                + " VALUES ('2', 'Second Note', 'Text 002')";

        sqLiteDatabase.execSQL(insert_subject_student);
    }
}