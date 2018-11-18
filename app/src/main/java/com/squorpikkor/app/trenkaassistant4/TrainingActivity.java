package com.squorpikkor.app.trenkaassistant4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {


//-----------------Created by Squorpikkor 28.10.18---------------------------//

//-----------------Первая активити-------------------------------------------//
//-----------------для отображения списка всех тренировок--------------------//

    /**    Общая концепция:
     *          Три главных активити:
     *              1. TrainingActivity -- для отображения списка всех тренировок
     *              2. TrainingExercisesActivity -- для отображения списка всех всех упражнений текущей трениировки
     *              3. ExerciseActivity -- для отображения и редактирования текущего упражнения
     *          Три таблицы БД:
     *              1. Training
     *              2. ExerciseData
     *              3. ExerciseNames
     */

    ListView lvTraining;
    ArrayList<Training> trainingList = new ArrayList<>();
    TrainingAdapter trainingAdapter;
    DatabaseHelper databaseHelper;
    Button newTrainingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        newTrainingButton = findViewById(R.id.new_training_button);
        databaseHelper = new DatabaseHelper(this);

        lvTraining = findViewById(R.id.training_lv);

        trainingAdapter = new TrainingAdapter(this, R.layout.training_lv_item, trainingList);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.new_training_button:
                        addNewTraining();
                }
            }
        };

        //Лисенер для элемента ListView
        lvTraining.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TrainingExerciseActivity.class);
                int pos = trainingList.get((int)id).getID();
                intent.putExtra("id", pos);
                startActivity(intent);
            }
        });


        newTrainingButton.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        trainingList.clear();
//        trainingList.add(new Training(18, 11, 16));
        trainingList.addAll(databaseHelper.getAllTrainings());
        lvTraining.setAdapter(trainingAdapter);

    }

    void addNewTraining() {
        //todo делать через метод, преобразующего сегодняшнюю дату в инты
        //todo или через алерт, в котором будет стоять сегодняшняя дата, с возможностью её редактирования
        databaseHelper.addNewTrainingToBD(2018, 11,11);
        trainingList.clear();
        trainingList.addAll(databaseHelper.getAllTrainings());
        lvTraining.setAdapter(trainingAdapter);

    }

}
