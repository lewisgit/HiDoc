package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.Model.RegisterModel;
import com.happydoc.lewis.myapplication.Presenter.LoginPresenter;
import com.happydoc.lewis.myapplication.Presenter.RegisterPresenter;
import com.happydoc.lewis.myapplication.View.LoginView;
import com.happydoc.lewis.myapplication.View.RegisterView;

/**
 * Created by Lewis on 2016/7/12.
 */
public class registerActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterModel registerModel=new RegisterModel();
        RegisterView registerView=new RegisterView(this);
        new RegisterPresenter(registerView,registerModel);
    }
}
