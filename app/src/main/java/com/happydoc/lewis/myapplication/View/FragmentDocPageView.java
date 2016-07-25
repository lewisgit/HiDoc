package com.happydoc.lewis.myapplication.View;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.Bean.ConsultInfo;
import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.account.DoctorInfo;
import com.happydoc.lewis.myapplication.event.Event;
import com.happydoc.lewis.myapplication.event.FollowEvent;
import com.happydoc.lewis.myapplication.event.StartConsultEvent;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LewisMS on 7/25/2016.
 */
public class FragmentDocPageView extends FragmentView {
    Button followBtn,onceBtn,weekBtn;
    TextView docName,docTitle,docHosp,docIntro,doc1price,doc7price;
    CircleImageView circleImageView;
    public FragmentDocPageView(Fragment fragment,View view){
        super(fragment,view);
    }
    public void setView(){
        //text
        docName=(TextView)getView(R.id.doc_name_page);
        docTitle=(TextView)getView(R.id.doc_title_page);
        docHosp=(TextView)getView(R.id.doc_agency_page);
        docIntro=(TextView)getView(R.id.doc_intro_page);
        doc1price=(TextView)getView(R.id.ask_once_price);
        doc7price=(TextView)getView(R.id.week_service_price);

        //btn
        followBtn =(Button)getView(R.id.follow_doc);
        onceBtn=(Button)getView(R.id.pay_once);
        weekBtn=(Button)getView(R.id.pay_week);

        //image
        circleImageView=(CircleImageView)getView(R.id.profile_image);


    }
    public void showDocInfo(DoctorInfo doctorInfo){
        if(docName!=null) docName.setText(doctorInfo.getName());
        if(docTitle!=null) docTitle.setText(doctorInfo.getDocTitle());
        if(docHosp!=null)docHosp.setText(doctorInfo.getDocHosp());
        if(docIntro!=null)docIntro.setText(doctorInfo.getDocIntro());

        if(doc1price!=null)doc1price.setText(doctorInfo.getDocOnceFee());
        if(doc7price!=null)doc7price.setText(doctorInfo.getDocWeekFee());

        if(circleImageView!=null) ImageLoader.getInstance().displayImage(doctorInfo.getAvatarUrl(),circleImageView, GlobalInfos.getDisplayImageOptions());

    }
    public void setFollow(){
        followBtn.setBackgroundResource(R.drawable.corners_gray);
        //isFollow=true;
        followBtn.setText(fragment.getString(R.string.unfollow));
    }
    public void setUnfollow(){
        followBtn.setBackgroundResource(R.drawable.corners);
        //isFollow=false;
        if(fragment.isAdded())
        followBtn.setText(fragment.getString(R.string.follow));
    }
    public void setConBtn(int type){
        switch (type){
            case 1:
                onceBtn.setBackgroundResource(R.drawable.corners_orangered);
                onceBtn.setText(fragment.getString(R.string.pay_now));
                weekBtn.setBackgroundResource(R.drawable.corners_orangered);
                weekBtn.setText(fragment.getString(R.string.pay_now));
                break;
            case 2:
                onceBtn.setBackgroundResource(R.drawable.corners_blue);
                onceBtn.setText(fragment.getString(R.string.chat_now));
                weekBtn.setBackgroundResource(R.drawable.corners_orangered);
                weekBtn.setText(fragment.getString(R.string.pay_now));
                break;
            case 3:
                onceBtn.setBackgroundResource(R.drawable.corners_blue);
                onceBtn.setText(fragment.getString(R.string.chat_now));
                weekBtn.setBackgroundResource(R.drawable.corners_blue);
                weekBtn.setText(fragment.getString(R.string.chat_now));
                break;
            case 0:
                onceBtn.setBackgroundResource(R.drawable.corners_orangered);
                onceBtn.setText(fragment.getString(R.string.pay_now));
                weekBtn.setBackgroundResource(R.drawable.corners_orangered);
                weekBtn.setText(fragment.getString(R.string.pay_now));
                break;

        }
    }
    public void setOnClickListener(){
        onceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartConsultEvent event=new StartConsultEvent();
                event.setServiceTyp(StartConsultEvent.ONCE_CONSULT);
                EventBus.getDefault().post(event);
            }
        });
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartConsultEvent event=new StartConsultEvent();
                event.setServiceTyp(StartConsultEvent.WEEK_CONSULT);
                EventBus.getDefault().post(event);
            }
        });
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowEvent event=new FollowEvent();
                EventBus.getDefault().post(event);
            }
        });
    }
}
