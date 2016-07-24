package com.happydoc.lewis.myapplication.Presenter;

import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.View.MeInfoView;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoPresenter {
    private MeInfoView view;
    private UserInfoModel model;
    public MeInfoPresenter(MeInfoView view,UserInfoModel model){
        this.view=view;
        this.model=model;
    }
}
