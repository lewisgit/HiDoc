package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.Presenter.MePresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeView;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeFragment extends GeneralFragment {
    //private MeView view;
    private MePresenter presenter;
    //private UserInfoModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_me,container,false);
        MeView view=new MeView(this,myView);
        UserInfoModel model=new UserInfoModel();
        presenter=new MePresenter(view,model);
        setPresenter(presenter);
        return myView;
    }
}
