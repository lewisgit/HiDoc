package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.SearchModel;
import com.happydoc.lewis.myapplication.Presenter.SearchPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.SearchView;

/**
 * Created by Lewis on 2016/7/27.
 */
public class DocSearchFragment extends Fragment{
    SearchPresenter presenter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_search,container,false);
        presenter=new SearchPresenter(new SearchView(this,myView), new SearchModel());
        return myView;
    }

}
