package com.happydoc.lewis.myapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Lewis on 2016/7/15.
 */
public class MotherView {
    public AppCompatActivity activity;
    public MotherView(AppCompatActivity activity){
        this.activity=activity;
    }
    public void showMsg(int id){
        Toast.makeText(activity, activity.getText(id), Toast.LENGTH_SHORT).show();
    }
    public void showMsg(String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
    public View getView(int id) {
        return activity.findViewById(id);
    }
}
