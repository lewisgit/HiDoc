package com.happydoc.lewis.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;
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

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Lewis on 2016/7/23.
 */
public class MeInfoFragment extends Fragment {
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
        return myView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == presenter.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if(!mSelectPath.isEmpty()){

                }
                //presenter.showSelectedImgs(mSelectPath);
            }
        }else{

        }
    }
}
