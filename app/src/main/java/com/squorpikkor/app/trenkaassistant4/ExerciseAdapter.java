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

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    private LayoutInflater inflater;
    private int layout;
    private List<Exercise> sourceList;

    ExerciseAdapter(Context context, int resource, List<Exercise> sourceList) {
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

        TextView nameView = view.findViewById(R.id.name);
        TextView activityView = view.findViewById(R.id.activity);
        TextView elementView = view.findViewById(R.id.element);

        Exercise state = sourceList.get(position);

        nameView.setText(state.getName());
        activityView.setText(String.format("%.5f" , state.getActivity()) + " кБк");
        //activityView.setText(String.valueOf(state.getActivity()));
        elementView.setText(state.getElement());

        Log.e("LOGG!!",  "position: " + position);

        return view;
    }
}
