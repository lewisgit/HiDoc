package com.happydoc.lewis.myapplication.Presenter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.happydoc.lewis.myapplication.Bean.GlobalInfos;
import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeInfoView;
import com.happydoc.lewis.myapplication.event.EditUserInfoEvent;
import com.happydoc.lewis.myapplication.event.MyCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoPresenter {
    private MeInfoView view;
    private UserInfoModel model;
    public static int REQUEST_CODE=2;
    public MeInfoPresenter(MeInfoView view,UserInfoModel model){
        this.view=view;
        this.model=model;
        EventBus.getDefault().register(this);
        initView();
    }
    public void initView(){
        view.setView();
        view.setOnClickListener();
        view.showInfo(GlobalInfos.getUserInfo());
    }

    public void onEvent(EditUserInfoEvent event){
        switch (event.getEventId()){
            case R.id.edit_avatar:
                choosePic();
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
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, final CharSequence text) {
                        //setSex(text.toString());
                        model.editSex(text.toString(), new MyCallBack<Exception>() {
                            @Override
                            public void done(Exception data) {
                                if (data == null) {
                                    view.showMsg(R.string.age_success);
                                    view.getUserSex().setText(text.toString());
                                }else{view.showMsg(R.string.set_fail);}
                            }
                        });
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
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                        if (input.length() > 0) {
                            model.editName(input.toString(), new MyCallBack<Exception>() {
                                @Override
                                public void done(Exception data) {
                                    if (data == null) {
                                        view.showMsg(R.string.nickname_success);
                                        view.getUserName().setText(input.toString());
                                    }else{view.showMsg(R.string.set_fail);}
                                }
                            });
                        }
                    }
                }).show();
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
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                        if (input.length() > 0) {
                            model.editCareer(input.toString(), new MyCallBack<Exception>() {
                                @Override
                                public void done(Exception data) {
                                    if (data == null) {
                                        view.showMsg(R.string.occuption_success);
                                        view.getUserCareer().setText(input.toString());
                                    } else {
                                        view.showMsg(R.string.set_fail);
                                    }
                                }
                            });
                        }
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
                    public void onInput(@NonNull MaterialDialog dialog,final CharSequence input) {
                        if (input.length() > 0) {
                            model.editAge(input.toString(), new MyCallBack<Exception>() {
                                @Override
                                public void done(Exception data) {
                                    if (data == null) {
                                        view.showMsg(R.string.age_success);
                                        view.getUserAge().setText(input.toString());
                                    } else {
                                        view.showMsg(R.string.set_fail);
                                    }
                                }
                            });
                        }
                        //view.showMsg("success");
                    }
                }).show();
    }

    public void choosePic(){

        Intent intent = new Intent(view.fragment.getActivity(), MultiImageSelectorActivity.class);
// whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
// default select images (support array list)
        //intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        view.fragment.startActivityForResult(intent, REQUEST_CODE);
        /*MultiImageSelector.create()
                .single()
                .start(view.fragment.getActivity(),REQUEST_CODE);*/
    }
    public void cropPic(Uri srcUri,Uri desUri){
        Bundle mCropOptionsBundle=new Bundle();

        UCrop.Options options=new UCrop.Options();
        options.setStatusBarColor(Color.BLACK);
        options.setToolbarColor(0xFF83DCFF);
        options.setActiveWidgetColor(0xFF83DCFF);
        mCropOptionsBundle.putParcelable(UCrop.EXTRA_INPUT_URI, srcUri);
        mCropOptionsBundle.putParcelable(UCrop.EXTRA_OUTPUT_URI, desUri);
        mCropOptionsBundle.putInt(UCrop.EXTRA_MAX_SIZE_X, 500);
        mCropOptionsBundle.putInt(UCrop.EXTRA_MAX_SIZE_Y, 500);
        mCropOptionsBundle.putFloat(UCrop.EXTRA_ASPECT_RATIO_X, 1);
        mCropOptionsBundle.putFloat(UCrop.EXTRA_ASPECT_RATIO_Y, 1);
        mCropOptionsBundle.putAll(options.getOptionBundle());
        Intent mCropIntent=new Intent();
        mCropIntent.setClass(view.fragment.getActivity(),UCropActivity.class);
        mCropIntent.putExtras(mCropOptionsBundle);
        view.fragment.startActivityForResult(mCropIntent, UCrop.REQUEST_CROP);

        /*UCrop.of(srcUri, desUri).withAspectRatio(1,1)
                .withMaxResultSize(500, 500)
                .withOptions()
                .start(view.fragment.getActivity());*/
    }
    public void setImg(final Uri uri){

        if(AVUser.getCurrentUser()!=null){
        File imgFile = new File(uri.getPath());
        String name= AVUser.getCurrentUser().getMobilePhoneNumber() + "_" +"Avatar" +"_" + System.currentTimeMillis();
        try{
            final AVFile file=AVFile.withFile(name,imgFile);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        AVUser usr=AVUser.getCurrentUser();
                        usr.put("Photo",file);
                        usr.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e==null){
                                    ImageLoader.getInstance().displayImage("file://"+uri.getPath(),view.getUserAvatar(),GlobalInfos.getDisplayImageOptions());
                                    view.showMsg(R.string.photo_success);
                                }
                                else view.showMsg(R.string.set_fail);
                            }
                        });
                    }else{view.showMsg(R.string.set_fail);}
                }
            });
        }catch (FileNotFoundException e){
            view.showMsg(R.string.set_fail);
            Log.d("error",e.toString());}
        }else{
            view.showMsg(R.string.set_fail);
        }
    }
}
