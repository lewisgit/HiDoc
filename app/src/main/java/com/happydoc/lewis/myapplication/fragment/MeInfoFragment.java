package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.Presenter.MeInfoPresenter;
import com.happydoc.lewis.myapplication.Presenter.MePresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeInfoView;
import com.happydoc.lewis.myapplication.View.MeView;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoFragment extends Fragment {
    MeInfoPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_myinfo,container,false);
        MeInfoView view=new MeInfoView(this,myView);
        UserInfoModel model=new UserInfoModel();
        presenter=new MeInfoPresenter(view,model);
        return myView;
    }
}
