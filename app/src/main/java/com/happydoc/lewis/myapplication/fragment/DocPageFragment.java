package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.DocPageModel;
import com.happydoc.lewis.myapplication.Model.FragmentDocListModel;
import com.happydoc.lewis.myapplication.Presenter.DocPagePresenter;
import com.happydoc.lewis.myapplication.Presenter.FragmentDocListPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.FragmentDocListView;
import com.happydoc.lewis.myapplication.View.FragmentDocPageView;

/**
 * Created by Lewis on 2016/7/17.
 */
public class DocPageFragment extends GeneralFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_docpage,container,false);
        FragmentDocPageView view=new FragmentDocPageView(this,myView);
        DocPageModel model=new DocPageModel();
        setPresenter(new DocPagePresenter(view,model));
        //new FragmentDocListPresenter(new FragmentDocListView(this,myView),new FragmentDocListModel());
        return myView;
    }
}
