package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.SetModel;
import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.Presenter.MeInfoPresenter;
import com.happydoc.lewis.myapplication.Presenter.SetPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeInfoView;
import com.happydoc.lewis.myapplication.View.SetView;
import com.happydoc.lewis.myapplication.fragmentinfo.FragmentInfo;

/**
 * Created by Lewis on 2016/7/23.
 */
public class SettingFragment extends Fragment {
    SetPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.framgent_settings,container,false);
        SetView view=new SetView(this,myView);
        SetModel model=new SetModel();
        presenter=new SetPresenter(view,model);
        return myView;
    }
}
