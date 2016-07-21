package com.happydoc.lewis.myapplication.event;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Lewis on 2016/7/20.
 */
public class SelectImgEvent {
    public Activity activity;
    public int requestCode;
    public SelectImgEvent(Activity activity,int requestCode){
        this.activity=activity;
        this.requestCode=requestCode;
    }
}
