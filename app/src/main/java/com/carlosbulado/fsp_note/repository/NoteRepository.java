package com.carlosbulado.fsp_note.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NoteRepository extends BaseRepository<Note>
{
    public NoteRepository(Context context, String tableName, HashMap<String, String> tableColumns, String id) {
        super(context, tableName, tableColumns, id);
    }

    public NoteRepository(SQLiteDatabase database, String tableName, HashMap<String, String> tableColumns, String id) {
        super(database, tableName, tableColumns, id);
    }

    public NoteRepository (Context context)
    {
        super(context, APP.NOTE.TABLENAME, APP.NOTE.TABLE_COLUMNS, APP.NOTE.ID);
    }

    public NoteRepository (SQLiteDatabase database)
    {
        super(database, APP.NOTE.TABLENAME, APP.NOTE.TABLE_COLUMNS, APP.NOTE.ID);
    }

    @Override
    protected ContentValues getAllValues(Note note)
    {
        ContentValues values = new ContentValues();

        values.put(APP.NOTE.ID, note.getId());
        values.put(APP.NOTE.TITLE, note.getTitle());
        values.put(APP.NOTE.TEXT, note.getText());
        values.put(APP.NOTE.LATITUDE, note.getLatitude());
        values.put(APP.NOTE.LONGITUDE, note.getLongitude());
        values.put(APP.NOTE.CREATEDDATE, APP.formatDate(new Date()));
        values.put(APP.NOTE.UPDATEDATE, APP.formatDate(new Date()));
        values.put(APP.NOTE.CATEGORY, note.getCategory());

        return values;
    }

    @Override
    protected Note loadObject (Cursor linesCursor)
    {
        Note note = new Note();
        note.setId(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.ID)));
        note.setTitle(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.TITLE)));
        note.setText(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.TEXT)));
        note.setLatitude(linesCursor.getDouble(linesCursor.getColumnIndex(APP.NOTE.LATITUDE)));
        note.setLongitude(linesCursor.getDouble(linesCursor.getColumnIndex(APP.NOTE.LONGITUDE)));
        note.setCreated(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.CREATEDDATE)));
        note.setUpdated(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.UPDATEDATE)));
        note.setCategory(linesCursor.getString(linesCursor.getColumnIndex(APP.NOTE.CATEGORY)));

        return note;
    }

    public ArrayList<Note> getAll(String whereText)
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + this.getTableName() + " WHERE " + APP.NOTE.TITLE + " LIKE '%" + whereText + "%' OR " + APP.NOTE.TEXT + " LIKE '%" + whereText + "%'", null);

        ArrayList<Note> objs = new ArrayList<>();
        if (mCursor != null && mCursor.getCount() > 0)
        {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast())
            {
                objs.add(this.loadObject(mCursor));
                mCursor.moveToNext();
            }
        }

        return objs;
    }
}
