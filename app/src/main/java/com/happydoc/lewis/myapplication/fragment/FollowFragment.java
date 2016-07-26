package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.FollowModel;
import com.happydoc.lewis.myapplication.Presenter.FollowPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.FollowView;

/**
 * Created by Lewis on 2016/7/26.
 */
public class FollowFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_follow,container,false);
        new FollowPresenter(new FollowView(this,myView),new FollowModel());
        return myView;
    }
}
