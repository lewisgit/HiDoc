package com.happydoc.lewis.myapplication.Presenter;

import com.happydoc.lewis.myapplication.Model.SetModel;
import com.happydoc.lewis.myapplication.View.SetView;

/**
 * Created by Lewis on 2016/7/23.
 */
public class SetPresenter {
    public SetView view;
    public SetModel model;
    public SetPresenter(SetView view,SetModel model){
        this.view=view;
        this.model=model;
        initView();
    }
    public void initView(){
        view.setView();
        view.setBtnListener();
    }
}
