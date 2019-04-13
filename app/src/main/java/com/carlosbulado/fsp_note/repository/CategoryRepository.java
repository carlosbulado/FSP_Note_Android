package com.carlosbulado.fsp_note.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Category;

import java.util.Date;
import java.util.HashMap;

public class CategoryRepository extends BaseRepository<Category>
{
    public CategoryRepository(Context context, String tableName, HashMap<String, String> tableColumns, String id) {
        super(context, tableName, tableColumns, id);
    }

    public CategoryRepository(SQLiteDatabase database, String tableName, HashMap<String, String> tableColumns, String id) {
        super(database, tableName, tableColumns, id);
    }

    public CategoryRepository (Context context)
    {
        super(context, APP.CATEGORY.TABLENAME, APP.CATEGORY.TABLE_COLUMNS, APP.CATEGORY.ID);
    }

    public CategoryRepository (SQLiteDatabase database)
    {
        super(database, APP.CATEGORY.TABLENAME, APP.CATEGORY.TABLE_COLUMNS, APP.CATEGORY.ID);
    }

    @Override
    protected ContentValues getAllValues(Category category) {
        ContentValues values = new ContentValues();

        values.put(APP.CATEGORY.ID, category.getId());
        values.put(APP.CATEGORY.TEXT, category.getText());
        values.put(APP.CATEGORY.CREATEDDATE, APP.formatDate(new Date()));
        values.put(APP.CATEGORY.UPDATEDATE, APP.formatDate(new Date()));

        return values;
    }

    @Override
    protected Category loadObject(Cursor linesCursor) {
        Category category = new Category();
        category.setId(linesCursor.getString(linesCursor.getColumnIndex(APP.CATEGORY.ID)));
        category.setText(linesCursor.getString(linesCursor.getColumnIndex(APP.CATEGORY.TEXT)));
        category.setCreated(linesCursor.getString(linesCursor.getColumnIndex(APP.CATEGORY.CREATEDDATE)));
        category.setUpdated(linesCursor.getString(linesCursor.getColumnIndex(APP.CATEGORY.UPDATEDATE)));

        return category;
    }
}
