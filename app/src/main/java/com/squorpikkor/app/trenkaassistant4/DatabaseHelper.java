package com.squorpikkor.app.trenkaassistant4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 11/01/2016.
 */

//todo добавить схему БАЗЫ ДАННЫХ (как рисунок)

public class DatabaseHelper extends SQLiteOpenHelper {

    //---------------Database and Tables-------------------------------------
    private static final String DATABASE_TRAINING_ASSISTANT = "training_database";
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
    //------------------------------------------------------------
    private static final String KEY_NAME = "name";
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

    DatabaseHelper(Context context) {
        super(context, DATABASE_TRAINING_ASSISTANT, null, DATABASE_VERSION);
    }


    //---------------Создание таблиц-----------------------------------------
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
        //---------best_weight и best_weight_count находятся в этой таблице, так как для всех
        //---------тренировок текущего упражнения это значение одно и то же. Т.е. нет смысла
        //---------хранить одинаковое значение во всех строках таблицы exercise_data
        //---------Но так оно хранится во всех строках exercise_names tables
        //---------Можно хранить только в первой строке, остальные оставлять пустыми
        String CREATE_EXERCISE_NAMES_TABLE = "CREATE TABLE " + TABLE_EXERCISES_NAMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_BEST_WEIGHT + " REAL,"
                + KEY_BEST_WEIGHT_COUNT + " INTEGER" + ")";
        String CREATE_EXERCISE_DATA_TABLE = "CREATE TABLE " + TABLE_EXERCISE_DATA + "("
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
        db.execSQL(CREATE_EXERCISE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRAINING + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EXERCISE_DATA + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EXERCISES_NAMES + "'");
        onCreate(db);
    }


//-----------------------TrainingActivity-------------------------------------------------
//-----------------------TRAINING TABLE-------------------------------------------------

    //todo в классе TimeConverter сделать метод, преобразующий текущую дату в три инта
    public void addNewTrainingToBD(int year, int month, int day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, year);
        values.put(KEY_MONTH, month);
        values.put(KEY_DAY, day);
        db.insert(TABLE_TRAINING, null, values);
        db.close();
    }

    public Training getTraining(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING, new String[]{KEY_ID,
                        KEY_YEAR,
                        KEY_MONTH,
                        KEY_DAY,
                        KEY_COMPLETE_EXR_COUNT,
                        KEY_RECORD_EXR_COUNT,
                        KEY_USER_WEIGHT
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Training training = new Training();
        if (cursor != null) {
            cursor.moveToFirst();

            training.setID(cursor.getInt(0));
            training.setYear(cursor.getInt(1));
            training.setMonth(cursor.getInt(2));
            training.setDay(cursor.getInt(3));
            //todo в классе траининг allExercise, а в Хелпере -- completeExr. Надо сделать метод подсчета колличества упражнений для Траининга
            training.setAllExerciseCount(cursor.getInt(4));
            training.setNewRecordCount(cursor.getInt(5));
            training.setUserWeight(cursor.getDouble(6));
            //todo где будет UserName? Наверное в отдельной таблице/ShPref, ведь это поле одинаковое для всех Траининг
        }
        if (cursor != null) {
            cursor.close();
        }

        return training;
    }


    public ArrayList<Training> getAllTrainings() {
        ArrayList<Training> trainingList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                //todo сделать нормально -- через getTraining, чтобы без повторного кода
                training.setID(cursor.getInt(0));
                training.setYear(cursor.getInt(1));
                training.setMonth(cursor.getInt(2));
                training.setDay(cursor.getInt(3));
                training.setAllExerciseCount(cursor.getInt(4));
                training.setNewRecordCount(cursor.getInt(5));
                training.setUserWeight(cursor.getDouble(6));
                trainingList.add(training);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return trainingList;
    }

    //TODO убрать параметр в методе?
    @SuppressWarnings("UnusedReturnValue")
    public int updateTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, training.getYear());
        values.put(KEY_MONTH, training.getMonth());
        values.put(KEY_DAY, training.getDay());
        values.put(KEY_COMPLETE_EXR_COUNT, training.getAllExerciseCount());
        values.put(KEY_RECORD_EXR_COUNT, training.getNewRecordCount());
        values.put(KEY_USER_WEIGHT, training.getUserWeight());

        return db.update(TABLE_TRAINING, values, KEY_ID + " = ?",
                new String[]{String.valueOf(training.getID())});
    }

    @SuppressWarnings("unused")
    public void deleteTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAINING, KEY_ID + " = ?", new String[]{String.valueOf(training.getID())});
        db.close();
    }

    @SuppressWarnings("unused")
    public void deleteAllTrainings() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAINING, null, null);
        db.close();
    }

    @SuppressWarnings("unused")
    public int getTrainingsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAINING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

//-----------------------TrainingExercises-------------------------------------------------
//---------------------EXERCISE DATA TABLE-------------------------------------------------

//            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + KEY_DATE + " INTEGER,"
//            + KEY_EXR_NAME + " INTEGER,"
//            + KEY_WEIGHT_1 + " REAL,"
//            + KEY_WEIGHT_1_COUNT + " INTEGER,"
//            + KEY_WEIGHT_2 + " REAL,"
//            + KEY_WEIGHT_2_COUNT + " INTEGER,"
//            + KEY_WEIGHT_3 + " REAL,"
//            + KEY_WEIGHT_3_COUNT + " INTEGER,"
//            + KEY_WEIGHT_4 + " REAL,"
//            + KEY_WEIGHT_4_COUNT + " INTEGER,"  //возможно, сделать REAL -- для бега (расстояние), с другой стороны, расстояние будет измеряться в меирах
//            + KEY_IS_COMPLETE +

    //старый вариант
    //date как интегер, потому как date в этой таблице является Foreign key, сами данные в таблице Training
    /*public void addNewExerciseToBD(int date, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_NAME, name);
        db.insert(TABLE_EXERCISE_DATA, null, values);
        db.close();
    }*/

    //Создание нового упражнения
    //В таблицу Exercise names добавляется имя нового упражнения,
    // а в таблицу exercise data добавляется id имени по таблице exercise names (foreign key)
    public void addNewExerciseToBD(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        values.put(KEY_NAME, name);
        db.insert(TABLE_EXERCISES_NAMES, null, values);

        values2.put(KEY_EXR_NAME, getExercisesNamesCount());
        db.insert(TABLE_EXERCISE_DATA, null, values);

        db.close();
    }

    public Exercise getExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISE_DATA, new String[]{
                        KEY_ID,
                        KEY_DATE,
                        KEY_EXR_NAME,
                        KEY_WEIGHT_1,
                        KEY_WEIGHT_1_COUNT,
                        KEY_WEIGHT_2,
                        KEY_WEIGHT_2_COUNT,
                        KEY_WEIGHT_3,
                        KEY_WEIGHT_3_COUNT,
                        KEY_WEIGHT_4,
                        KEY_WEIGHT_4_COUNT,
                        KEY_IS_COMPLETE
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Exercise exercise = new Exercise();
        if (cursor != null) {
            cursor.moveToFirst();

            //todo может count'ы сделать double? Чтоб можно было сделать дочерний класс для бега и хранить в каунте дистанцию?(0.4 км, например)
            exercise.setID(cursor.getInt(0));
            exercise.setDate(cursor.getInt(1));
            exercise.setName(cursor.getString(2));
            exercise.setWeight_1(cursor.getDouble(3));
            exercise.setCount_1(cursor.getInt(4));
            exercise.setWeight_2(cursor.getDouble(5));
            exercise.setCount_2(cursor.getInt(6));
            exercise.setWeight_3(cursor.getDouble(7));
            exercise.setCount_3(cursor.getInt(8));
            exercise.setWeight_4(cursor.getDouble(9));
            exercise.setCount_4(cursor.getInt(10));
            exercise.setStatus(cursor.getInt(11));
        }
        if (cursor != null) {
            cursor.close();
        }

        return exercise;
    }

    //todo вообще в методе загрузки ВСЕХ exercises (а этот метод будет по-логике только в TrainingExerciseActivity) нет необходимости загружать из БД ВСЕХ данных, ведь в этой активити будет только список всех exercises
    public ArrayList<Exercise> getAllExercises() {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXERCISE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                //todo сделать нормально -- используя метод getTraining, чтобы без повторного кода
                exercise.setID(cursor.getInt(0));
                exercise.setDate(cursor.getInt(1));
                exercise.setName(cursor.getString(2));
                exercise.setWeight_1(cursor.getDouble(3));
                exercise.setCount_1(cursor.getInt(4));
                exercise.setWeight_2(cursor.getDouble(5));
                exercise.setCount_2(cursor.getInt(6));
                exercise.setWeight_3(cursor.getDouble(7));
                exercise.setCount_3(cursor.getInt(8));
                exercise.setWeight_4(cursor.getDouble(9));
                exercise.setCount_4(cursor.getInt(10));
                exercise.setStatus(cursor.getInt(11));
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return exerciseList;
    }

    //todo сделать getAllExercises() только с некоторыми параметрами (не со всеми), чтоб загружать в exerciseList


    //TODO убрать параметр в методе?
    @SuppressWarnings("UnusedReturnValue")
    public int updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_DATE, exercise.getDate());  //дату наверное не надо перезаписывать
        values.put(KEY_EXR_NAME, exercise.getName());
        values.put(KEY_WEIGHT_1, exercise.getWeight_1());
        values.put(KEY_WEIGHT_1_COUNT, exercise.getCount_1());
        values.put(KEY_WEIGHT_2, exercise.getWeight_2());
        values.put(KEY_WEIGHT_2_COUNT, exercise.getCount_2());
        values.put(KEY_WEIGHT_3, exercise.getWeight_3());
        values.put(KEY_WEIGHT_3_COUNT, exercise.getCount_3());
        values.put(KEY_WEIGHT_4, exercise.getWeight_4());
        values.put(KEY_WEIGHT_4_COUNT, exercise.getCount_4());
        values.put(KEY_IS_COMPLETE, exercise.getStatus());

        return db.update(TABLE_EXERCISE_DATA, values, KEY_ID + " = ?",
                new String[]{String.valueOf(exercise.getID())});
    }

    @SuppressWarnings("unused")
    public void deleteExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISE_DATA, KEY_ID + " = ?", new String[]{String.valueOf(exercise.getID())});
        db.close();
    }

    //----------Метод не будет использоваться, оставил для заметки. Возможно этот код попадет в мои шаблоны
    @SuppressWarnings("unused")
    public void deleteAllExercise() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISE_DATA, null, null);
        db.close();
    }

    @SuppressWarnings("unused")
    public int getExerciseCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EXERCISE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


//---------------------EXERCISE NAMES TABLE-------------------------------------------------

/*    //todo в классе TimeConverter сделать метод, преобразующий текущую дату в три инта
    public void addNewExerciseNameToBD(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EXR_NAME, name);
        db.insert(TABLE_EXERCISES_NAMES, null, values);
        db.close();
    }

    //выбирается по инту, хранящегося в таблице Exercise_Data в столбце key_name (т.е. по foreign key)
    public Training getExerciseName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING, new String[]{KEY_ID,
                        KEY_YEAR,
                        KEY_MONTH,
                        KEY_DAY,
                        KEY_COMPLETE_EXR_COUNT,
                        KEY_RECORD_EXR_COUNT,
                        KEY_USER_WEIGHT
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Training training = new Training();
        if (cursor != null) {
            cursor.moveToFirst();

            training.setID(cursor.getInt(0));
            training.setYear(cursor.getInt(1));
            training.setMonth(cursor.getInt(2));
            training.setDay(cursor.getInt(3));
            //todo в классе траининг allExercise, а в Хелпере -- completeExr. Надо сделать метод подсчета колличества упражнений для Траининга
            training.setAllExerciseCount(cursor.getInt(4));
            training.setNewRecordCount(cursor.getInt(5));
            training.setUserWeight(cursor.getDouble(6));
            //todo где будет UserName? Наверное в отдельной таблице/ShPref, ведь это поле одинаковое для всех Траининг
        }
        if (cursor != null) {
            cursor.close();
        }

        return training;
    }


    public ArrayList<Training> getAllTrainings(){
        ArrayList<Training> trainingList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRAINING;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                //todo сделать нормально -- через getTraining, чтобы без повторного кода
                training.setID(cursor.getInt(0));
                training.setYear(cursor.getInt(1));
                training.setMonth(cursor.getInt(2));
                training.setDay(cursor.getInt(3));
                training.setAllExerciseCount(cursor.getInt(4));
                training.setNewRecordCount(cursor.getInt(5));
                training.setUserWeight(cursor.getDouble(6));
                trainingList.add(training);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return trainingList;
    }

    //TODO убрать параметр в методе?
    @SuppressWarnings("UnusedReturnValue")
    public int updateTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_YEAR, training.getYear());
        values.put(KEY_MONTH, training.getMonth());
        values.put(KEY_DAY, training.getDay());
        values.put(KEY_COMPLETE_EXR_COUNT, training.getAllExerciseCount());
        values.put(KEY_RECORD_EXR_COUNT, training.getNewRecordCount());
        values.put(KEY_USER_WEIGHT, training.getUserWeight());

        return db.update(TABLE_TRAINING, values, KEY_ID + " = ?",
                new String[]{String.valueOf(training.getID())});
    }

    @SuppressWarnings("unused")
    public void deleteTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAINING, KEY_ID + " = ?", new String[]{String.valueOf(training.getID())});
        db.close();
    }

    @SuppressWarnings("unused")
    public void deleteAllTrainings() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAINING, null, null);
        db.close();
    }
    */

    public int getExercisesNamesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EXERCISES_NAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount(); //todo а будет ли работать, если курсор закрыт?
    }

    //todo реализация через одно место :(((((((
    public int getExercisesNamesLastID() {
        int lastRow;
        String countQuery = "SELECT  * FROM " + TABLE_EXERCISES_NAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        lastRow = cursor.getCount();
        Cursor cursor2 = db.query(TABLE_TRAINING, new String[]{KEY_ID
                }, KEY_ID + "=?",
                new String[]{String.valueOf(lastRow)}, null, null, null, null);

        if (cursor2 != null) {
            cursor2.moveToFirst();

        }
        int lastID = cursor2.getInt(0);
        cursor.close();
        cursor2.close();
        return lastID;
    }

}
