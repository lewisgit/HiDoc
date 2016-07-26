package com.happydoc.lewis.myapplication.Presenter;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeInfoView;
import com.happydoc.lewis.myapplication.event.EditUserInfoEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoPresenter {
    private MeInfoView view;
    private UserInfoModel model;

    public MeInfoPresenter(MeInfoView view,UserInfoModel model){
        this.view=view;
        this.model=model;
        EventBus.getDefault().register(this);
        initView();
    }
    public void initView(){
        view.setView();
        view.setOnClickListener();
    }

    public void onEvent(EditUserInfoEvent event){
        switch (event.getEventId()){
            case R.id.profile_image:
                break;
            case R.id.edit_name:
                setName();
                break;
            case R.id.edit_sex:
                editSex();
                break;
            case R.id.edit_phone:
                view.showMsg(R.string.phoneset_unaval);
                break;
            case R.id.edit_career:
                setCareer();
                break;
            case R.id.edit_age:
                setAge();
                break;
        }
    }
    public void editSex(){
        if(view.fragment.isAdded())
        new MaterialDialog.Builder(view.fragment.getActivity())
                .title(R.string.choose_sex)
                .items(R.array.sex_choice)
                .widgetColorRes(R.color.doc_blue)
                .positiveColorRes(R.color.doc_blue)
                .negativeColorRes(R.color.doc_blue)
                .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        setSex(text.toString());
                        return false;
                    }
                }).positiveText(R.string.set).negativeText(R.string.cancel_set).show();
    }
    public void setName(){
        new MaterialDialog.Builder(view.fragment.getActivity())
                .title(R.string.set_name)
                .inputRange(0, 10)
                .positiveText(R.string.set)
                .widgetColorRes(R.color.doc_blue)
                .positiveColorRes(R.color.doc_blue)
                .negativeColorRes(R.color.doc_blue)
                .negativeText(R.string.cancel_set)
                .input("","",false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input.length()==0)
                            view.showMsg("fail");
                        else
                            view.showMsg("success");
                    }
                }).show();
    }
    public void setSex(String sex){
        view.showMsg("success");
    }
    public void setCareer(){
        new MaterialDialog.Builder(view.fragment.getActivity())
                .title(R.string.set_career)
                .inputRange(0, 10)
                .positiveText(R.string.set)
                .widgetColorRes(R.color.doc_blue)
                .positiveColorRes(R.color.doc_blue)
                .negativeColorRes(R.color.doc_blue)
                .negativeText(R.string.cancel_set)
                .input("","",false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input.length()==0)
                            view.showMsg("fail");
                        else
                            view.showMsg("success");
                    }
                }).show();
    }
    public void setAge(){
        new MaterialDialog.Builder(view.fragment.getActivity())
                .title(R.string.set_age)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRange(1,3)
                .positiveText(R.string.set)
                .widgetColorRes(R.color.doc_blue)
                .positiveColorRes(R.color.doc_blue)
                .negativeColorRes(R.color.doc_blue)
                .negativeText(R.string.cancel_set)
                .input("","",false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input.length()==0)
                            view.showMsg("fail");
                        else
                            view.showMsg("success");
                    }
                }).show();
    }
}
