package com.squorpikkor.app.trenkaassistant4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TrainingExerciseActivity extends AppCompatActivity {

    //todo сделать нормальное название

    //todo сделать кнопку (свитч) отображать/скрыть итемы со статусом "не буду сегодня делать"

    /**
     * Активити для отображения всех упражнений текущей тренировки (дня)
     * Концепция -- количество итем зависит от колличества строк таблицы exercise_names, т.е. кол-ва имен
     * информация для каждого итема загружается из таблицы exercise_data выбирая только те строки,
     * дата которых (в этой таблице это инт) совпадает с ID, переданного от активити Трейнинг
     * (другими словами выбираются только упражнения текущего дня)
     * затем каждое упражнение присваивается тому итему листвью, имя которого (инт) совпадает
     * с номером строки таблицы exercise_names
     *
     * т.е. загружается список объектов по таблице имен в ЭррейЛист (на этом этапе у объектов есть только имена), у каждого объекта листа
     * теперь есть порядковый номер -- он же foreign key для таблицы exercise_data
     * Из этой таблицы (exercise_data) в лист передаются данные для каждого объекта,
     * при этом из таблицы e_data через where выбираются только строки с датой, совпадающей с ID,
     * из оставшихся строк каждому объекту присваиваются данные -- порядковый номер объекта равен
     * имени (это ведь инт) в строке
     */

    private int trainingID;
    Training training;
    DatabaseHelper databaseHelper;
    Button saveButton;
    ArrayList<Exercise> exerciseList;
    ListView lvExercise;
    ExerciseNamesAdapter exerciseNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exercise);

        saveButton = findViewById(R.id.add_exercise_button);
        lvExercise = findViewById(R.id.exercises_names_lv);
        databaseHelper = new DatabaseHelper(this);

        //-------Получаю ID для exercise из MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trainingID = extras.getInt("id");
        }
        // если 0, то добавление
        if (trainingID > 0) {
            training = databaseHelper.getTraining(trainingID);
        } else {
            // скрываем кнопку удаления
//            deleteButton.setVisibility(View.GONE);
        }

        training = databaseHelper.getTraining(trainingID);

        exerciseNamesAdapter = new ExerciseNamesAdapter(this, R.id.exercises_names_lv, exerciseList);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add_exercise_button:
                        addNewExercise("Жим лежа");
                        break;
                }
            }
        };

        saveButton.setOnClickListener(listener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseList.clear();
        exerciseList.addAll(databaseHelper.getAllExercises());
        lvExercise.setAdapter(exerciseNamesAdapter);

    }

    //Создать новое упражнение и добавить его (имя) в БД
    private void addNewExercise(String name) {
        databaseHelper.addNewExerciseToBD(name);
        exerciseList.clear();
        exerciseList.addAll(databaseHelper.getAllExercises());

    }


}
