package com.squorpikkor.app.trenkaassistant4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TrainingExerciseActivity extends AppCompatActivity {

    //todo сделать нормальное название
    /**
     * Активити для отображения всех упражнений текущей тренировки (дня)
     *
     */

    private int trainingID;
    Training training;
    DatabaseHelper databaseHelper;
    Button saveButton;
    ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exercise);

        saveButton = findViewById(R.id.save_button);

        databaseHelper = new DatabaseHelper(this);

        //-------Получаю ID для ra_source из MainActivity
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
        setTextViewByRa_source();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.save_button:
                        setRa_sourceByTextView();
                        databaseHelper.updateTraining(training);
//                        goHome();
                        break;
//                    case R.id.deleteButton:
//                        ad.show();
//                        break;
                }
            }
        };

        saveButton.setOnClickListener(listener);
//        deleteButton.setOnClickListener(listener);

    }

    private void setRa_sourceByTextView() {
//        ra_source.setName(nameBox.getText().toString());
//        ra_source.setElement(elementBox.getText().toString());
//        ra_source.setA0(Double.parseDouble(a0Box.getText().toString()));
//        ra_source.setHalfLife(Double.parseDouble(halfLifeBox.getText().toString()));
//        ra_source.setYear(Integer.parseInt(yearBox.getText().toString()));
//        ra_source.setMonth(Integer.parseInt(monthBox.getText().toString()));
//        ra_source.setDay(Integer.parseInt(dayBox.getText().toString()));

    }

    private void setTextViewByRa_source() {
//        nameBox.setText(ra_source.getName());
//        elementBox.setText(ra_source.getElement());
//        a0Box.setText(String.valueOf(ra_source.getA0()));
//        halfLifeBox.setText(String.valueOf(ra_source.getHalfLife()));
//        yearBox.setText(String.valueOf(ra_source.getYear()));
//        monthBox.setText(String.valueOf(ra_source.getMonth()));
//        dayBox.setText(String.valueOf(ra_source.getDay()));
    }
}
