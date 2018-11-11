package com.squorpikkor.app.trenkaassistant4.old_stuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
/**
public class Database2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sourceManager";
    private static final String TABLE_SOURCES = "ra_sources";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ELEMENT = "element";
    private static final String COLUMN_A0 = "a0";
    private static final String COLUMN_HALF_LIFE = "half_life";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_DAY = "day";

    //По поводу ID: при создании нового Training ID у него ещё нет, как только создается
    //экземпляр класса, он сразу же заносится в БД. ID объекта ещё нет, в базе ID уже есть
    //как же загрузить конкретный объект класса, если для этого нужно знать его ID?
    //Очень просто. После того, как объект загружается в БД, вызывается метод getAll
    //Все объекты загружаются в лист, а адаптер обновляет ListView, таким образом только что
    //созданный объект класса появляется в активити как элемент списка. При этом в момент загрузки
    //из БД методом getAll объект получает свой ID. Voila

    Database2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SOURCES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_ELEMENT + " TEXT,"
                + COLUMN_A0 + " REAL,"
                + COLUMN_HALF_LIFE + " REAL,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_MONTH + " INTERGER,"
                + COLUMN_DAY + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCES);
        onCreate(db);
    }

    public void addTraining() {
//    public void addRA_Source(Training ra_source) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Source");
        values.put(COLUMN_ELEMENT, "Cesium");
        values.put(COLUMN_A0, 90);
        values.put(COLUMN_HALF_LIFE, 30.17);
        values.put(COLUMN_YEAR, 2016);
        values.put(COLUMN_MONTH, 10);
        values.put(COLUMN_DAY, 17);
        db.insert(TABLE_SOURCES, null, values);
        db.close();
    }

    public Training getTraining(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SOURCES, new String[]{COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_ELEMENT,
                        COLUMN_A0,
                        COLUMN_HALF_LIFE,
                        COLUMN_YEAR,
                        COLUMN_MONTH,
                        COLUMN_DAY
                }, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Training training = new Training();
        if (cursor != null) {
            cursor.moveToFirst();

            training.setID(Integer.parseInt(cursor.getString(0)));
            training.setName(cursor.getString(1));
            training.setElement(cursor.getString(2));
            training.setA0(Double.parseDouble(cursor.getString(3)));
            training.setHalfLife(Double.parseDouble(cursor.getString(4)));
            training.setYear(Integer.parseInt(cursor.getString(5)));
            training.setMonth(Integer.parseInt(cursor.getString(6)));
            training.setDay(Integer.parseInt(cursor.getString(7)));
        }

        if (cursor != null) {
            cursor.close();
        }

        return training;
    }

    public List<Training> getAllTraining() {
        List<Training> sourceList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SOURCES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.setID(Integer.parseInt(cursor.getString(0)));
                training.setName(cursor.getString(1));
                training.setElement(cursor.getString(2));
                training.setA0(Double.parseDouble(cursor.getString(3)));
                training.setHalfLife(Double.parseDouble(cursor.getString(4)));
                training.setYear(Integer.parseInt(cursor.getString(5)));
                training.setMonth(Integer.parseInt(cursor.getString(6)));
                training.setDay(Integer.parseInt(cursor.getString(7)));
                sourceList.add(training);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return sourceList;
    }

    //TODO сделать void?
    @SuppressWarnings("UnusedReturnValue")
    public int updateRA_Source(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, training.getName());
        values.put(COLUMN_ELEMENT, training.getElement());
        values.put(COLUMN_A0, training.getA0());
        values.put(COLUMN_HALF_LIFE, training.getHalfLife());
        values.put(COLUMN_YEAR, training.getYear());
        values.put(COLUMN_MONTH, training.getMonth());
        values.put(COLUMN_DAY, training.getDay());

        return db.update(TABLE_SOURCES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(training.getID())});
    }

    public void deleteTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SOURCES, COLUMN_ID + " = ?", new String[]{String.valueOf(training.getID())});
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
 **/