package com.cseacademia.mominulcse1213.cseacademia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mominul on 10/28/2018.
 */

public class TeacherList extends ArrayAdapter<TeacherConstructor>{
    private Activity context;
    List<TeacherConstructor> teachers;

    public TeacherList(Activity context, List<TeacherConstructor>teachers)
    {
        super(context, R.layout.teacher_list_show,teachers);
        this.context=context;
        this.teachers=teachers;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.teacher_list_show, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.vtname);
        TextView textViewDesig = (TextView) listViewItem.findViewById(R.id.vtdesignation);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.vtemail);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.vtphone);

        TeacherConstructor teacher = teachers.get(position);
        textViewName.setText(teacher.getTeacherName());
        textViewDesig.setText(teacher.getTeacherDesignation());
        textViewEmail.setText(teacher.getTeacherEmail());
        textViewPhone.setText(teacher.getTeacherPhone());

        return listViewItem;
    }

}
