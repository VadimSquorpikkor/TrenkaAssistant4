package com.squorpikkor.app.trenkaassistant4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 26-Apr-17.
 */
public class TrainingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Training> trainingList;

    public TrainingAdapter(Context context, ArrayList<Training> trainingList) {

        this.context = context;
        this.trainingList = trainingList;
    }


    @Override
    public int getCount() {
        return trainingList.size();
    }

    @Override
    public Object getItem(int position) {
        return trainingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.training_lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvhobby = (TextView) convertView.findViewById(R.id.hobby);
            holder.tvcity = (TextView) convertView.findViewById(R.id.city);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(trainingList.get(position).getYear());
        holder.tvhobby.setText(trainingList.get(position).getMonth());
        holder.tvcity.setText(trainingList.get(position).getDay());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvhobby, tvcity;
    }

}