package com.happydoc.lewis.myapplication.Presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.happydoc.lewis.myapplication.Model.CircleSendModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.CircleSendView;
import com.happydoc.lewis.myapplication.circle.mvp.modle.CircleModel;
import com.happydoc.lewis.myapplication.event.SelectImgEvent;
import com.happydoc.lewis.myapplication.event.SendCircleEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Lewis on 2016/7/20.
 */
public class CircleSendPresenter {
    CircleSendView view;
    CircleSendModel model;
    static int maxImgNum=9;
    public static int REQUEST_CODE=2;
    public static int SEND_SUCESS=1;
    public static int SEND_FAIL=0;
    public CircleSendPresenter(CircleSendView view,CircleSendModel model){
        this.view=view;
        this.model=model;
        initalize();
    }
    public void initalize(){
        view.setView();
        view.setViewAdapter();
        view.setViewListener();
        view.eventBus=new EventBus();
        view.eventBus.register(this);
    }
   // @Subscribe
    public void onEvent(SelectImgEvent event){
        MultiImageSelector.create()
                .count(maxImgNum)
                .multi()
                .origin(model.getSelectedImgs())
                .start(event.activity,REQUEST_CODE);
    }

  //  @Subscribe
    public void onEvent(SendCircleEvent event){
        new Thread(runnable).start();
    }

    public void showSelectedImgs(ArrayList<String> arrayList){
        model.setSelectedImgs(arrayList);
       // view.adapterGrid.clear();
        view.adapterGrid.swapDatas(arrayList);
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
            ArrayList<AVFile> files=new ArrayList<>();
            for(String url:model.getSelectedImgs())
            {
                File imgFile = new File(url);
                String name = AVUser.getCurrentUser().getMobilePhoneNumber() + "_" + System.currentTimeMillis();
                try{
                    AVFile file=AVFile.withFile(name,imgFile);
                    file.save();
                    files.add(file);
                }catch (FileNotFoundException e){Log.d("error",e.toString());}
            }
            AVObject album = new AVObject("Album");
            album.put("albumContent",view.getInputTxt());
            album.put("comments", new ArrayList<AVObject>());
            album.put("digUsers", new ArrayList<AVObject>());
            album.put("creator", AVUser.getCurrentUser());
            album.put("isDel", false);
            album.put("albumPhotos", files);
            album.save();
            Message msg=new Message();
                msg.what=R.string.send_circle_success;
                sendCompleteHandler.sendMessage(msg);
                //view.showMsg(R.string.send_circle_success);
            }catch (AVException e){Log.d("error",e.toString());
                Message msg=new Message();
                msg.what=R.string.send_circle_fail;
                sendCompleteHandler.sendMessage(msg);
            //view.showMsg(R.string.send_circle_fail);
            }
        }
    };
        Handler sendCompleteHandler=new Handler(){
            public void handleMessage(Message msg){
                view.showMsg(msg.what);
                switch (msg.what){
                    case R.string.send_circle_success:
                        break;
                    case R.string.send_circle_fail:
                        break;
                }
            }
        };
}
