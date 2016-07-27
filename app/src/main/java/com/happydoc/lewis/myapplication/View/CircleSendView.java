package com.happydoc.lewis.myapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.adapter.AdapaterCridImage;
import com.happydoc.lewis.myapplication.event.SelectImgEvent;
import com.happydoc.lewis.myapplication.event.SendCircleEvent;

import de.greenrobot.event.EventBus;
import org.w3c.dom.Text;

/**
 * Created by Lewis on 2016/7/20.
 */
public class CircleSendView extends MotherView {
    GridView gridView;
    EditText editText;
    Button selectBtn;
    TextView sendBtn;
    ImageView backBtn;
    public AdapaterCridImage adapterGrid;

    private static final int REQUEST_IMAGE = 2;
    public EventBus eventBus;
    public CircleSendView(AppCompatActivity activity){
        super(activity);
    }
    public void setViewAdapter(){
        gridView.setNumColumns(3);
        adapterGrid=new AdapaterCridImage(activity.getApplication());
        gridView.setAdapter(adapterGrid);
    }
    public void setViewListener(){
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {eventBus.post(new SelectImgEvent(activity,REQUEST_IMAGE));}});
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventBus.post(new SendCircleEvent());
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
    public void setView(){
        gridView=(GridView)getView(R.id.img_grid);
        editText=(EditText)getView(R.id.circle_content);
        selectBtn=(Button) getView(R.id.select_img);
        sendBtn=(TextView)getView(R.id.send_circle);
        backBtn=(ImageView)getView(R.id.back_circle);
    }
    public void setEventBus(EventBus eventBus){
        this.eventBus=eventBus;
    }
    public String getInputTxt(){
        return editText.getText().toString();
    }
}
