package com.squorpikkor.app.trenkaassistant4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 11/01/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

//---------------Database and Tables-------------------------------------
    private static String DATABASE_TRAINING_ASSISTANT = "training_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TRAINING = "training";
    private static final String TABLE_EXERCISE_DATA = "exercise_data";
    private static final String TABLE_EXERCISES_NAMES = "exercises_names";
//---------------Keys----------------------------------------------------
    private static final String KEY_ID = "id";
    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";
    private static final String KEY_COMPLETE_EXR_COUNT = "all_count";
    private static final String KEY_RECORD_EXR_COUNT = "record";
    private static final String KEY_USER_WEIGHT = "u_weight";
    private static final String KEY_NAME = "u_weight";
    private static final String KEY_BEST_WEIGHT = "best_w";
    private static final String KEY_BEST_WEIGHT_COUNT = "best_ц_с";
    private static final String KEY_DATE = "date";
    private static final String KEY_EXR_NAME = "exr_name";
    private static final String KEY_WEIGHT_1 = "w1";
    private static final String KEY_WEIGHT_1_COUNT = "c1";
    private static final String KEY_WEIGHT_2 = "w2";
    private static final String KEY_WEIGHT_2_COUNT = "c2";
    private static final String KEY_WEIGHT_3 = "w3";
    private static final String KEY_WEIGHT_3_COUNT = "c3";
    private static final String KEY_WEIGHT_4 = "w4";
    private static final String KEY_WEIGHT_4_COUNT = "c4";
    private static final String KEY_IS_COMPLETE = "is_comlete";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_TRAINING_ASSISTANT, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRAINING_TABLE = "CREATE TABLE " + TABLE_TRAINING + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_YEAR + " INTEGER,"
                + KEY_MONTH + " INTEGER,"
                + KEY_DAY + " INTEGER,"
                + KEY_COMPLETE_EXR_COUNT + " INTEGER,"
                + KEY_RECORD_EXR_COUNT + " INTEGER,"
                + KEY_USER_WEIGHT + " REAL" + ")";
        String CREATE_EXERCISE_NAMES_TABLE = "CREATE TABLE " + TABLE_EXERCISES_NAMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_BEST_WEIGHT + " REAL,"
                + KEY_BEST_WEIGHT_COUNT + " INTEGER" + ")";
        String CREATE_EXRECISE_DATA_TABLE = "CREATE TABLE " + TABLE_EXERCISE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE + " INTEGER,"
                + KEY_EXR_NAME + " INTEGER,"
                + KEY_WEIGHT_1 + " REAL,"
                + KEY_WEIGHT_1_COUNT + " INTEGER,"
                + KEY_WEIGHT_2 + " REAL,"
                + KEY_WEIGHT_2_COUNT + " INTEGER,"
                + KEY_WEIGHT_3 + " REAL,"
                + KEY_WEIGHT_3_COUNT + " INTEGER,"
                + KEY_WEIGHT_4 + " REAL,"
                + KEY_WEIGHT_4_COUNT + " INTEGER,"  //возможно, сделать REAL -- для бега (расстояние), с другой стороны, расстояние будет измеряться в меирах
                + KEY_IS_COMPLETE + " INTEGER" + ")";   //типа boolean нет в SQLite, будет: 1=true, 0=false
        db.execSQL(CREATE_TRAINING_TABLE);
        db.execSQL(CREATE_EXERCISE_NAMES_TABLE);
        db.execSQL(CREATE_EXRECISE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRAINING + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EXERCISE_DATA + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EXERCISES_NAMES + "'");
        onCreate(db);
    }

    public void addUser(String name, String hobby, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        // db.insert(TABLE_TRAINING, null, values);
        long id = db.insertWithOnConflict(TABLE_TRAINING, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        //adding user hobby in users_hobby table
        ContentValues valuesHobby = new ContentValues();
        valuesHobby.put(KEY_ID, id);
        valuesHobby.put(KEY_HOBBY, hobby);
        db.insert(TABLE_EXERCISE_DATA, null, valuesHobby);

        //adding user city in users_city table
        ContentValues valuesCity = new ContentValues();
        valuesCity.put(KEY_ID, id);
        valuesCity.put(KEY_CITY, city);
        db.insert(TABLE_EXERCISES_NAMES, null, valuesCity);
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));

                //getting user hobby where id = id from user_hobby table
                String selectHobbyQuery = "SELECT  * FROM " + TABLE_EXERCISE_DATA +" WHERE "+KEY_ID+" = "+ userModel.getId();
                Log.d("oppp",selectHobbyQuery);
                //SQLiteDatabase dbhobby = this.getReadableDatabase();
                Cursor cHobby = db.rawQuery(selectHobbyQuery, null);

                if (cHobby.moveToFirst()) {
                    do {
                        userModel.setHobby(cHobby.getString(cHobby.getColumnIndex(KEY_HOBBY)));
                    } while (cHobby.moveToNext());
                }

                //getting user city where id = id from user_city table
                String selectCityQuery = "SELECT  * FROM " + TABLE_EXERCISES_NAMES +" WHERE "+KEY_ID+" = "+ userModel.getId();;
                //SQLiteDatabase dbCity = this.getReadableDatabase();
                Cursor cCity = db.rawQuery(selectCityQuery, null);

                if (cCity.moveToFirst()) {
                    do {
                        userModel.setCity(cCity.getString(cCity.getColumnIndex(KEY_CITY)));
                    } while (cCity.moveToNext());
                }

                // adding to Students list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public void updateUser(int id, String name, String hobby, String city) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        db.update(TABLE_TRAINING, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        // updating hobby in users_hobby table
        ContentValues valuesHobby = new ContentValues();
        valuesHobby.put(KEY_HOBBY, hobby);
        db.update(TABLE_EXERCISE_DATA, valuesHobby, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        // updating city in users_city table
        ContentValues valuesCity = new ContentValues();
        valuesCity.put(KEY_CITY, city);
        db.update(TABLE_EXERCISES_NAMES, valuesCity, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting from users table
        db.delete(TABLE_TRAINING, KEY_ID + " = ?",new String[]{String.valueOf(id)});

        //deleting from users_hobby table
        db.delete(TABLE_EXERCISE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        //deleting from users_city table
        db.delete(TABLE_EXERCISES_NAMES, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

}
