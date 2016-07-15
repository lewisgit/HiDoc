package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happydoc.lewis.myapplication.Model.ForgetPwdModel;
import com.happydoc.lewis.myapplication.Model.PhoneVerifyModel;
import com.happydoc.lewis.myapplication.Presenter.ForgetPwdPresenter;
import com.happydoc.lewis.myapplication.Presenter.PhoneVerifyPresenter;
import com.happydoc.lewis.myapplication.View.ForgetPwdView;
import com.happydoc.lewis.myapplication.View.PhoneVerifyView;

/**
 * Created by Lewis on 2016/7/12.
 */
public class pwdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);
        ForgetPwdView view=new ForgetPwdView(this);
        ForgetPwdModel model=new ForgetPwdModel();
        new ForgetPwdPresenter(view,model);
    }
}
