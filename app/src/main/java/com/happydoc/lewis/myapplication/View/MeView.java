package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.Bean.UserInfo;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.ShowFragmentEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeView extends FragmentView{
    //@Bind(R.id.profile_image)
    public CircleImageView profile_image;

    //@Bind(R.id.nick_name)
    public TextView nickName;
    //@Bind(R.id.user_phonenum)
    public TextView phoneNum;
   // @Bind(R.id.edit_info)
    public RelativeLayout editInfo;
  //  @Bind(R.id.my_query)
    public RelativeLayout query;
  //  @Bind(R.id.my_follow_doc)
    public RelativeLayout myFollow;
  //  @Bind(R.id.setting)
    public RelativeLayout setting;
    public MeView(Fragment fragment, View view){
        super(fragment,view);
        ButterKnife.bind(view);
    }

    public void showInfo(UserInfo userInfo){
        if(userInfo.getUserName()!=null)nickName.setText(userInfo.getUserName());
        if(userInfo.getPhoneNum()!=null)phoneNum.setText(userInfo.getPhoneNum());
        if(userInfo.getUserUrl()!=null) ImageLoader.getInstance().displayImage(userInfo.getUserUrl(),profile_image, GlobalInfos.getDisplayImageOptions());

    }
    public void setView(){
        profile_image=(CircleImageView)getView(R.id.profile_image);
        nickName=(TextView)getView(R.id.nick_name);
        phoneNum=(TextView)getView(R.id.user_phonenum);
        editInfo=(RelativeLayout)getView(R.id.edit_info);
        query=(RelativeLayout)getView(R.id.my_query);
        setting=(RelativeLayout)getView(R.id.setting);
        myFollow=(RelativeLayout)getView(R.id.my_follow_doc);
    }
    public void setOnClickListner(){
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.info_script));
                GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.me_script)));
                //showMsg("test");
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.chatlist_script));
                GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.me_script)));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.set_script));
                GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.me_script)));
            }
        });
        myFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.myfollow_script));
                GlobalInfos.addBackStack(GlobalInfos.getFragmentList().get(fragment.getString(R.string.me_script)));
            }
        });
    }
 //   @OnClick(R.id.edit_info)
  //  public void goEditInfo(View v){
 //       MainActivityEventBus.getEventBus().post(new ShowFragmentEvent(R.string.me_script));
 //   }
}
