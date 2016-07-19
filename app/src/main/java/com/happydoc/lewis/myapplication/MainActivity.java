package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.Model.MainModel;
import com.happydoc.lewis.myapplication.Presenter.MainPresenter;
import com.happydoc.lewis.myapplication.View.MainActivityView;

/**
 * Created by Lewis on 2016/7/14.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainPresenter(new MainActivityView(this),new MainModel());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {}
}
