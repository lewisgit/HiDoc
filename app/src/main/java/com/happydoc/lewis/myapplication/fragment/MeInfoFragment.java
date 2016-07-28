package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happydoc.lewis.myapplication.Model.UserInfoModel;
import com.happydoc.lewis.myapplication.Presenter.MeInfoPresenter;
import com.happydoc.lewis.myapplication.Presenter.MePresenter;
import com.happydoc.lewis.myapplication.R;
import com.happydoc.lewis.myapplication.View.MeInfoView;
import com.happydoc.lewis.myapplication.View.MeView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoFragment extends GeneralFragment {
    MeInfoPresenter presenter;
    public static final int RESULT_OK           = -1;
    ArrayList<String> mSelectPath=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View myView=inflater.inflate(R.layout.fragment_myinfo,container,false);
        MeInfoView view=new MeInfoView(this,myView);
        UserInfoModel model=new UserInfoModel();
        presenter=new MeInfoPresenter(view,model);
        setPresenter(presenter);
        return myView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == presenter.REQUEST_CODE){

                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if(!mSelectPath.isEmpty()){
                    File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                    File srcFile=new File(mSelectPath.get(0));
                    presenter.cropPic(Uri.fromFile(srcFile),Uri.fromFile(tempFile));
                }
                //presenter.showSelectedImgs(mSelectPath);
            }else if(requestCode==UCrop.REQUEST_CROP){
                final Uri resultUri = UCrop.getOutput(data);
                presenter.setImg(resultUri);
            }
        }

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
