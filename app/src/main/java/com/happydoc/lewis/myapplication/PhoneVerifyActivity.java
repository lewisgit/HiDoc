package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.happydoc.lewis.myapplication.Model.PhoneVerifyModel;
import com.happydoc.lewis.myapplication.Presenter.PhoneVerifyPresenter;
import com.happydoc.lewis.myapplication.View.PhoneVerifyView;
import com.happydoc.lewis.myapplication.atts.TimerCount;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lewis on 2016/7/12.
 */
public class PhoneVerifyActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        PhoneVerifyView view=new PhoneVerifyView(this);
        PhoneVerifyModel model=new PhoneVerifyModel();
        new PhoneVerifyPresenter(view,model);
    }
}
