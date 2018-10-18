package com.squorpikkor.app.trenkaassistant4;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sourceManager";
    private static final String TABLE_SOURCES = "ra_sources";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    //По поводу ID: при создании нового Exercise ID у него ещё нет, как только создается
    //экземпляр класса, он сразу же заносится в БД. ID объекта ещё нет, в базе ID уже есть
    //как же загрузить конкретный объект класса, если для этого нужно знать его ID?
    //Очень просто. После того, как объект загружается в БД, вызывается метод getAll
    //Все объекты загружаются в лист, а адаптер обновляет ListView, таким образом только что
    //созданный объект класса появляется в активити как элемент списка. При этом в момент загрузки
    //из БД методом getAll объект получает свой ID. Voila

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SOURCES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCES);
        onCreate(db);
    }

    public void addRA_Source() {
//    public void addRA_Source(Exercise ra_source) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Source");
        db.insert(TABLE_SOURCES, null, values);
        db.close();
    }

    public Exercise getRA_Source(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SOURCES, new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                }, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Exercise exercise = new Exercise();
        if (cursor != null) {
            cursor.moveToFirst();

            exercise.setID(Integer.parseInt(cursor.getString(0)));
            exercise.setName(cursor.getString(1));
        }

        if (cursor != null) {
            cursor.close();
        }

        return exercise;
    }

    public List<Exercise> getAllRA_Sources() {
        List<Exercise> sourceList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SOURCES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setID(Integer.parseInt(cursor.getString(0)));
                exercise.setName(cursor.getString(1));
                sourceList.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return sourceList;
    }

    //TODO сделать void?
    @SuppressWarnings("UnusedReturnValue")
    public int updateRA_Source(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, exercise.getName());

        return db.update(TABLE_SOURCES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercise.getID())});
    }

    public void deleteRA_Source(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SOURCES, COLUMN_ID + " = ?", new String[]{String.valueOf(exercise.getID())});
        db.close();
    }

    @SuppressWarnings("unused")
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SOURCES, null, null);
        db.close();
    }

    @SuppressWarnings("unused")
    public int getRA_SourceCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SOURCES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

