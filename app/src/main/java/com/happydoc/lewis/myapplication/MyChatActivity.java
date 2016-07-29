package com.happydoc.lewis.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.activity.ChatFragment;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.happydoc.lewis.myapplication.fragment.MyChatFragment;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lewis on 2016/7/29.
 package com.avoscloud.leanchatlib.activity;

 import android.app.ActionBar;
 import android.content.Intent;
 import android.os.Bundle;

 import com.avoscloud.leanchatlib.R;
 import com.avos.avoscloud.im.v2.AVIMClient;
 import com.avos.avoscloud.im.v2.AVIMConversation;
 import com.avos.avoscloud.im.v2.AVIMException;
 import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
 import com.avoscloud.leanchatlib.controller.ChatManager;
 import com.avoscloud.leanchatlib.controller.ConversationHelper;
 import com.avoscloud.leanchatlib.model.ConversationType;
 import com.avoscloud.leanchatlib.utils.Constants;
 import com.avoscloud.leanchatlib.utils.LogUtils;

 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.Map;

 /**
 * Created by wli on 15/9/18.
 */
public class MyChatActivity extends AVBaseActivity {

    protected ChatFragment chatFragment;
    protected AVIMConversation conversation;
    @Bind(R.id.back_chat)
    public ImageView backBtn;
    @Bind(R.id.conv_name)
    public TextView convName;
    @Bind(R.id.conv_countdown)
    public TextView countView;
    @Bind(R.id.comment_btn)
    public LinearLayout commentBtn;

    private String docId,docName;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychat);
        chatFragment = (ChatFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_chat);
        initByIntent(getIntent());
        ButterKnife.bind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(Constants.MEMBER_ID)) {
                getConversation(extras.getString(Constants.MEMBER_ID));
            } else if (extras.containsKey(Constants.CONVERSATION_ID)) {
                String conversationId = extras.getString(Constants.CONVERSATION_ID);
                updateConversation(AVIMClient.getInstance(ChatManager.getInstance().getSelfId()).getConversation(conversationId));
            } else {}
        }
    }

    protected void initActionBar(String title) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            if (title != null) {
                actionBar.setTitle(title);
            }
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            LogUtils.i("action bar is null, so no title, please set an ActionBar style for activity");
        }
    }

    protected void updateConversation(final AVIMConversation conversation) {
        if (null != conversation) {
            setConvTitle(conversation);
            this.conversation = conversation;
            chatFragment.setConversation(conversation);
            //chatFragment.showUserName(ConversationHelper.typeOfConversation(conversation) != ConversationType.Single);
            chatFragment.showUserName(false);
            initActionBar(ConversationHelper.titleOfConversation(conversation));

            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                                Map<String, Object> dicParameters = new HashMap<>();
                                dicParameters.put("convid",conversation.getConversationId() );
                                // 调用云函数
                                AVCloud.callFunctionInBackground("convid_q", dicParameters, new FunctionCallback<Object>() {
                                    @Override
                                    public void done(Object o, AVException e) {
                                        if (e == null) {
                                            BigDecimal remTime_d=(BigDecimal)o;
                                            if(remTime_d==null){
                                                goBack(null);
                                                showMsg(R.string.wait_conv);
                                            }else {
                                                long remTime = remTime_d.longValue();
                                                Message message = new Message();
                                                Bundle data = new Bundle();
                                                data.putLong("rem", remTime);
                                                message.setData(data);
                                                handler.sendMessage(message);
                                            }
                                        } else {
                                            goBack(null);
                                            showMsg(R.string.wait_conv);
                                            Log.d("error found", e.getMessage());
                                        }
                                    }
                                });
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            };

            thread=new Thread(runnable);
            thread.start();

        }
    }

    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        ChatManager.getInstance().createSingleConversation(memberId, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (filterException(e)) {
                    ChatManager.getInstance().getRoomsTable().insertRoom(avimConversation.getConversationId());
                    updateConversation(avimConversation);
                }
            }
        });
    }
    @OnClick(R.id.back_chat)
    public void goBack(View v){
        this.onBackPressed();
        thread.interrupt();
    }
    @OnClick(R.id.comment_btn)
    public void goComment(View v){
        if(docId!=null){
        Intent intent=new Intent(this, CommentActivity.class);
        intent.putExtra("DoctorID",docId);
        startActivity(intent);
        }else{
            showMsg(R.string.comment_unaval);
        }
    }

    private void setConvTitle(AVIMConversation conversation){
        String docObjId=ConversationHelper.otherIdOfConversation(conversation);
        if(docObjId!=null){
            AVQuery<AVObject> query=new AVQuery<>("_User");
            query.whereEqualTo("objectId",docObjId);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if(e==null && list.size()>0)
                    {
                        docId=list.get(0).getString("mobilePhoneNumber");
                        docName=list.get(0).getString("Name");
                        convName.setText(docName);
                    }
                }
            });
        }
    }
    final Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data=msg.getData();
            long remTime=data.getLong("rem");
            if(remTime<=0){
                goBack(null);
                showMsg(R.string.conv_end);
            }
            if(remTime>0 && remTime<5) {
                showMsg(R.string.rem_5);

            }
            setRemTime(remTime);
        }
    };
    public void showMsg(int id){
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }
    public void setRemTime(long rem){
        Long remTime=rem;
        Long day=remTime/(24*60);
        remTime=remTime-24*60*day;
        Long hour=remTime/60;
        Long minute=remTime-60*hour;
        String txt=getString(R.string.rem)+day.toString()+getString(R.string.day)+hour.toString()+getString(R.string.hour)+minute.toString()+getString(R.string.minute);
        countView.setText(txt);
    }
}