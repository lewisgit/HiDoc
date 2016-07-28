package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.Bean.UserInfo;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.event.EditUserInfoEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoView extends FragmentView {
    TextView userName,userPhone,userSex,userAge,userCareer;
    ImageView userAvatar;
    RelativeLayout editName,editPhone,editSex,editAge,editCareer,editAvatar;
    public MeInfoView(Fragment fragment,View view){
        super(fragment,view);
    }
    public void setView(){
        userAge=(TextView)getView(R.id.user_age);
        userAvatar=(ImageView)getView(R.id.profile_image);
        userCareer=(TextView)getView(R.id.user_career);
        userSex=(TextView)getView(R.id.user_sex);
        userPhone=(TextView)getView(R.id.user_phone);
        userName=(TextView)getView(R.id.user_name);

        editAge=(RelativeLayout)getView(R.id.edit_age);
        editAvatar=(RelativeLayout)getView(R.id.edit_avatar);
        editCareer=(RelativeLayout)getView(R.id.edit_career);
        editSex=(RelativeLayout)getView(R.id.edit_sex);
        editPhone=(RelativeLayout)getView(R.id.edit_phone);
        editName=(RelativeLayout)getView(R.id.edit_name);
    }
    public void setOnClickListener(){
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EditUserInfoEvent(v.getId()));
            }
        };
        editAge.setOnClickListener(listener);
        editAvatar.setOnClickListener(listener);
        editSex.setOnClickListener(listener);
        editPhone.setOnClickListener(listener);
        editName.setOnClickListener(listener);
        editCareer.setOnClickListener(listener);
    }
    public void showInfo(UserInfo info){
        if(info!=null) {
            userAge.setText(info.getUserAge());
            userCareer.setText(info.getUserCareer());
            userPhone.setText(info.getPhoneNum());
            userSex.setText(info.getUserSex());
            userName.setText(info.getUserName());
            ImageLoader.getInstance().displayImage(info.getUserUrl(), userAvatar, GlobalInfos.getDisplayImageOptions());
        }
    }

    public TextView getUserAge() {
        return userAge;
    }

    public TextView getUserName() {
        return userName;
    }

    public TextView getUserSex() {
        return userSex;
    }

    public TextView getUserCareer() {
        return userCareer;
    }

    public ImageView getUserAvatar() {
        return userAvatar;
    }
}
