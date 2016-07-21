package com.happydoc.lewis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.Model.CircleSendModel;
import com.happydoc.lewis.myapplication.Presenter.CircleSendPresenter;
import com.happydoc.lewis.myapplication.View.CircleSendView;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Lewis on 2016/7/20.
 */
public class CircleSendActivity extends AppCompatActivity{
    CircleSendView view;
    CircleSendModel model;
    CircleSendPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cirlcesend);
        view=new CircleSendView(this);
        model=new CircleSendModel();
        presenter=new CircleSendPresenter(view,model);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == presenter.REQUEST_CODE){
            if(resultCode == RESULT_OK){
               ArrayList<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                presenter.showSelectedImgs(mSelectPath);
            }
        }
    }
}
