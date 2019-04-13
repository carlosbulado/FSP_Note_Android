package com.carlosbulado.fsp_note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.carlosbulado.fsp_note.activity.NotePageActivity;
import com.carlosbulado.fsp_note.app.APP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
                    + APP.NOTE.CATEGORY + " TEXT,"
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
        String android_uuid = UUID.randomUUID().toString();
        String insert_subject_student = "INSERT INTO " + APP.CATEGORY.TABLENAME
                + " ( " + APP.CATEGORY.ID + ", "
                + APP.CATEGORY.TEXT + ") "
                + " VALUES ('" + android_uuid + "', 'Android')";

        sqLiteDatabase.execSQL(insert_subject_student);


        String ios_uuid = UUID.randomUUID().toString();
        insert_subject_student = "INSERT INTO " + APP.CATEGORY.TABLENAME
                + " ( " + APP.CATEGORY.ID + ", "
                + APP.CATEGORY.TEXT + ") "
                + " VALUES ('" + ios_uuid + "', 'iOS')";

        sqLiteDatabase.execSQL(insert_subject_student);

        insert_subject_student = "INSERT INTO " + APP.NOTE.TABLENAME
                + " ( " + APP.NOTE.ID + ", "
                + APP.NOTE.TITLE + ", " +
                APP.NOTE.TEXT + ", " +
                APP.NOTE.CREATEDDATE + ", " +
                APP.NOTE.LATITUDE + ", " +
                APP.NOTE.LONGITUDE + ", " +
                APP.NOTE.CATEGORY + ") "
                + " VALUES ('" + UUID.randomUUID().toString() + "', 'First Note', 'Text 01', '" + APP.formatDate(new Date()) + "', '-20.271617279820017', '-40.289893448352814', '" + android_uuid + "')";

        sqLiteDatabase.execSQL(insert_subject_student);

        insert_subject_student = "INSERT INTO " + APP.NOTE.TABLENAME
                + " ( " + APP.NOTE.ID + ", "
                + APP.NOTE.TITLE + ", " +
                APP.NOTE.TEXT + ", " +
                APP.NOTE.CREATEDDATE + ", " +
                APP.NOTE.LATITUDE + ", " +
                APP.NOTE.LONGITUDE + ", " +
                APP.NOTE.CATEGORY + ") "
                + " VALUES ('" + UUID.randomUUID().toString() + "', 'Second Note', 'Text 02', '" + APP.formatDate(new Date()) + "', '-20.271617279820017', '-40.289893448352814', '" + ios_uuid + "')";

        sqLiteDatabase.execSQL(insert_subject_student);
    }
}