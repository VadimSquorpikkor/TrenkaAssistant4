package com.squorpikkor.app.trenkaassistant4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Squorpikkor 6/11/18
 */
public class TrainingAdapter extends ArrayAdapter<Training> {

    private LayoutInflater inflater;
    private int layout;
    private List<Training> sourceList;

    TrainingAdapter(Context context, int resource, List<Training> sourceList) {
        super(context, resource, sourceList);
        this.sourceList = sourceList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder")
        View view = inflater.inflate(this.layout, parent, false);

        TextView year = view.findViewById(R.id.year);
        TextView month = view.findViewById(R.id.month);
        TextView day = view.findViewById(R.id.day);

        Training state = sourceList.get(position);

        //todo сделать класс (TimeConverter) (и метод в нем), собирающий дату из интов в строку, с добавлением, где надо, нулей, точек: 1418 = 01.04ю18
        year.setText(String.valueOf(state.getYear()));
        month.setText(String.valueOf(state.getMonth()));
        day.setText(String.valueOf(state.getDay()));


        return view;
    }
}