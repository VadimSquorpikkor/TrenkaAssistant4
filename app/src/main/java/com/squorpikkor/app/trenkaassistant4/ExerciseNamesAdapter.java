package com.squorpikkor.app.trenkaassistant4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExerciseNamesAdapter  extends ArrayAdapter<Exercise> {

    private LayoutInflater inflater;
    private int layout;
    private List<Exercise> sourceList;

    ExerciseNamesAdapter(Context context, int resource, List<Exercise> sourceList) {
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

        TextView name = view.findViewById(R.id.name);

        Exercise state = sourceList.get(position);

        name.setText(state.getName());

        return view;
    }
}
