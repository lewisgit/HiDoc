package com.happydoc.lewis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lewis on 2016/7/29.
 */
public class CommentActivity extends AppCompatActivity{
    RatingBar ratingBar;
    Button postButton;
    EditText rateContent;
    float rateNumber=0;
    String docId;
    @Bind(R.id.back_comment)
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        Intent intent=getIntent();
        docId=intent.getExtras().getString("DoctorID");

        ratingBar = (RatingBar)findViewById(R.id.ratingbarId);
        ratingBar.setOnRatingBarChangeListener(new RatingBarListener());
        rateContent = (EditText) findViewById(R.id.rate_content);
        postButton = (Button)findViewById(R.id.post_feedback_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布提交到服务器 rateContent.getText(), rateNumber, 先DISable掉button，然后等待CALLBACK，成功后跳转
                //得到对话中病人和医生的PHONE NUMBER
                AVObject comment = new AVObject("Comment");
                AVUser user=AVUser.getCurrentUser();
                if(user!=null && rateNumber>0 && rateContent.getText().toString().length()>0){
                comment.put("PatientID", user.getMobilePhoneNumber());
                comment.put("DoctorID", docId);
                comment.put("comment", rateContent.getText().toString());
                comment.put("rating", rateNumber);
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        //Toast.makeText(RateActivity.this, getString(R.string.feedback_success), Toast.LENGTH_SHORT).show();
                       if(e==null){ showMsg(R.string.feedback_success);
                        //Intent intent = new Intent(RateActivity.this, mainActivity.class);
                        //startActivity(intent);
                        goBack();}else{
                           showMsg(R.string.comment_fail);
                       }
                    }
                });
            }else{
                    showMsg(R.string.empty_comment);
                }}
        });
    }

    private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

        public void onRatingChanged(RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            System.out.println("等级：" + rating);
            rateNumber = rating;
        }
    }
    public void showMsg(int id){
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }
    public void goBack(){
        this.onBackPressed();
    }

    @OnClick(R.id.back_comment)
    public void goBack(View v){
        this.onBackPressed();
    }
}
