package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.happydoc.lewis.myapplication.Model.FragmentDocListModel;
import com.happydoc.lewis.myapplication.Presenter.FragmentDocListPresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.FragmentDocListView;
import com.happydoc.lewis.myapplication.scrollimage.common.ScrollImageEntity;
import com.happydoc.lewis.myapplication.scrollimage.image.ImageScroll;
import com.happydoc.lewis.myapplication.scrollimage.image.ScrollImageController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Lewis on 2016/7/17.
 */
public class DocListFragment extends Fragment {

    private ScrollImageController scrollImageView;
    private List<ScrollImageEntity> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_doclist,container,false);
        new FragmentDocListPresenter(new FragmentDocListView(this,myView),new FragmentDocListModel());
        return myView;
    }
}
