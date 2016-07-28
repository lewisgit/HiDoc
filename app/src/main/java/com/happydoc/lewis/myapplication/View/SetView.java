package com.happydoc.lewis.myapplication.View;

import android.app.AlertDialog;
import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.event.MainActivityEventBus;
import com.happydoc.lewis.myapplication.event.SignOutEvent;

/**
 * Created by Lewis on 2016/7/23.
 */
public class SetView extends  FragmentView{

    RelativeLayout clearBtn,notifBtn,aboutBtn,updateBtn;
    Button signOutBtn;
    public SetView(Fragment fragment,View view){
        super(fragment,view);
    }
    public void setView(){
        clearBtn=(RelativeLayout)getView(R.id.set_clear);
        notifBtn=(RelativeLayout)getView(R.id.set_notify);
        aboutBtn=(RelativeLayout)getView(R.id.set_about);
        updateBtn=(RelativeLayout)getView(R.id.set_update);
        signOutBtn=(Button)getView(R.id.set_signout);
    }
    public void setBtnListener(){
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityEventBus.getEventBus().post(new SignOutEvent());
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg(R.string.clear_success);
            }
        });
        notifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg(R.string.setnotif_in_set);
            }
        });
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View aboutUs = View.inflate(fragment.getActivity(), R.layout.aboutus, null);
                new AlertDialog.Builder(fragment.getActivity()).setTitle(fragment.getString(R.string.aboutus)).setIcon(
                        android.R.drawable.ic_dialog_info).setView(aboutUs).setPositiveButton(fragment.getString(R.string.confirm), null).show();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg(R.string.latest_ver);
            }
        });
    }
}
