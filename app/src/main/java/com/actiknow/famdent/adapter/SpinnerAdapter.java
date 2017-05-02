package com.actiknow.famdent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actiknow.famdent.R;
import com.actiknow.famdent.utils.Utils;

import java.util.List;


public class SpinnerAdapter extends ArrayAdapter<String> {
    Activity activity;
    LayoutInflater inflater;
    TextView tvUserType;
    String[] user_type;

    public SpinnerAdapter (Context context, int resource) {
        super (context, resource);
    }

    public SpinnerAdapter (Context context, int resource, int textViewResourceId) {
        super (context, resource, textViewResourceId);
    }

    public SpinnerAdapter (Activity activity, int resource, String[] objects) {
        super (activity, resource, objects);
        this.activity = activity;
        user_type = objects;
        inflater = (LayoutInflater) activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    public SpinnerAdapter (Context context, int resource, int textViewResourceId, String[] objects) {
        super (context, resource, textViewResourceId, objects);
    }

    public SpinnerAdapter (Context context, int resource, List<String> objects) {
        super (context, resource, objects);
    }

    public SpinnerAdapter (Context context, int resource, int textViewResourceId, List<String> objects) {
        super (context, resource, textViewResourceId, objects);
    }


    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    public View getCustomView (int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate (R.layout.spinner_item, parent, false);
        tvUserType = (TextView) row.findViewById (R.id.tvUserType);
        if (position == 0) {
            tvUserType.setEnabled (false);
            tvUserType.setTextColor (activity.getResources ().getColor (R.color.text_color_grey_light));
        } else {
            tvUserType.setEnabled (true);
        }

        tvUserType.setText (user_type[position]);
        Utils.setTypefaceToAllViews (activity, tvUserType);
        return row;
    }
}
