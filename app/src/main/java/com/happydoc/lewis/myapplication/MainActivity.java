package com.happydoc.lewis.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.ThirdPartUserUtils;
import com.happydoc.lewis.myapplication.Model.MainModel;
import com.happydoc.lewis.myapplication.Presenter.MainPresenter;
import com.happydoc.lewis.myapplication.View.MainActivityView;
import com.happydoc.lewis.myapplication.account.CustomUserProvider;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.RefreshEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Lewis on 2016/7/14.
 */
public class MainActivity extends AppCompatActivity {
    long lastBackTime,BACK_INTERVAL=1000;
    //public EventBus eventBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeChat();
        new MainPresenter(new MainActivityView(this),new MainModel());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {}


    public void initializeChat(){

        ThirdPartUserUtils.setThirdPartUserProvider(CustomUserProvider.getInstance());
        ChatManager mchatManager=ChatManager.getInstance();
        mchatManager.init(getApplicationContext());
        mchatManager.openClient(this, AVUser.getCurrentUser().getObjectId(),new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null != e) {
                    showMsg(e.getMessage());
                }else{
                    showMsg(getString(R.string.init_consult_success));
                }
            }
        });
    }

    public void showMsg(String e){
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }
/*    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    public EventBus getEventBus() {
        return eventBus;
    }*/
@Override
public void onBackPressed() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastBackTime < BACK_INTERVAL) {
        EventBus.clearCaches();//try to solve the Presenter not recycled problem
        super.onBackPressed();
    } else {
        Toast.makeText(getApplicationContext(),getString(R.string.double_click_quit),Toast.LENGTH_SHORT).show();
    }
    lastBackTime = currentTime;
}

    @Override
    protected void onResume() {
        super.onResume();
        MainActivityEventBus.getEventBus().post(new RefreshEvent());
    }
}
