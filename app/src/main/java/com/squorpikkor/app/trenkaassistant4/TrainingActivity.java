package com.squorpikkor.app.trenkaassistant4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {


//-----------------Created by Squorpikkor 28.10.18---------------------------//

//-----------------Первая активити-------------------------------------------//
//-----------------для отображения списка всех тренировок--------------------//

    /**    Общая концепция:
     *          Три главных активити:
     *              1. TrainingActivity -- для отображения списка всех тренировок
     *              2. TrainingActivity -- для отображения списка всех всех упражнений текущей трениировки
     *              3. TrainingActivity -- для отображения и редактирования текущего упражнения
     *          Три таблицы БД:
     *              1. Training
     *              2. ExerciseData
     *              3. ExerciseNames
     */

    ListView lvTraining;
    ArrayList<Training> trainingList = new ArrayList<>();
    TrainingAdapter trainingAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        databaseHelper = new DatabaseHelper(this);

        lvTraining = findViewById(R.id.training_lv);

        //Необычный конструктор для адаптера -- представление для итема задается внутри класса
        // адаптера, а не в параметре конструктора
        trainingAdapter = new TrainingAdapter(this, trainingList);





    }


}
