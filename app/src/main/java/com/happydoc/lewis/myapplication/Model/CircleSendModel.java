package com.happydoc.lewis.myapplication.Model;

import java.util.ArrayList;

/**
 * Created by Lewis on 2016/7/20.
 */
public class CircleSendModel {
    private ArrayList<String> selectedImgs;
    public void setSelectedImgs(ArrayList<String> arrayList){selectedImgs=arrayList;}
    public  ArrayList<String> getSelectedImgs(){
        if(selectedImgs==null)
        {
            selectedImgs=new ArrayList<String>();
        }
        return  selectedImgs;
    }
}
