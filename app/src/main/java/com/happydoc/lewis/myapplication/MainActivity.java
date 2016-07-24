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

import de.greenrobot.event.EventBus;

/**
 * Created by Lewis on 2016/7/14.
 */
public class MainActivity extends AppCompatActivity {

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
                    showMsg("咨询系统初始化成功");
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
}
