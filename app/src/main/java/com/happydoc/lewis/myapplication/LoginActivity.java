package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.Model.LoginModel;
import com.happydoc.lewis.myapplication.Presenter.LoginPresenter;
import com.happydoc.lewis.myapplication.View.LoginView;

/**
 * Created by Lewis on 2016/7/11.
 */
public class LoginActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginModel loginModel=new LoginModel();
        LoginView loginView=new LoginView(this);
        new LoginPresenter(loginView,loginModel);
    }
}
